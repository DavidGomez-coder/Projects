import Menu.Menu;
import Packages.Packet;
import Packages.PacketFactory;
import TID.TID;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * CLIENTE TFPT ITERATIVO
 * David Gómez Pérez
 */
public class TFTPClient {
    /*  CONTROL DE TIMEOUT */
    private static final int MAXTRIES = 5;
    private static final int TIMEOUT = 1000;
    /* PATRONES DE COMANDOS*/
    private static final String putTransfer = "put[\\ \t]+[a-zA-Z_][a-zA-Z0-9_\\.]*";
    private static final String getTransfer = "get[\\ \t]+[a-zA-Z_][a-zA-Z0-9_\\.]*";
    private static final String filePatron = "[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+";
    private static final String get_head = "get[\\ \t]+";
    private static final String put_head = "put[\\ \t]+";
    private static final String connect_patron = "connect[\\ \t]+[a-zA-Z0-9_\\.]+";
    private static final String connect_head = "connect[\\ \t]+";
    private static int LOST_PACKETS = 0;
    private static int RETR_PACKETS = 0;
    private static int SEND_PACKETS = 0;
    /*  SOCKET Y BUFFERS */
    private static DatagramSocket socket;
    private static DatagramPacket sendPacket;
    private static DatagramPacket receivePacket;
    /*  INETADDRESS Y PUERTO DEL SERVIDOR*/
    private static int serverPort;
    private static String serverIP;
    private static String hostName;
    private static InetAddress serverAddress;
    /* TID PARA COMPROBACIONES */
    private static TID<Integer, Integer> tid;
    /* FACTORY PARA LA CREACIÓN DE PAQUETES */
    private static PacketFactory factory;
    /* MAP PARA GUARDAR MENSAJES */
    private static Map<String, String> receiveMss;
    /* BOOLEANO FIN DE FICHERO */
    private static boolean EOF = false;

    /* MODO DE TRANSMISIÓN DE FICHERO */
    private static String mode = "octet";

    /* BOOLEANO QUE INDICA SI ESTÁ O NO CONECTADO */
    private static boolean connect = false;

    /* MENU */
    private static Menu menu;

    public static void main(String[] args) throws IOException {
        //inicializamos variables
        menu = new Menu();
        factory = new PacketFactory();
        //iniciamos la línea de comandos
        startCommandLine();
        //hemos terminado
        System.out.println("Cliente cerrado con éxito");
    }

    //=================
    //LÍNEA DE COMANDOS
    //=================

    /**
     * Este método se encarga de mostrar la interfaz del cliente durante su ejecución. Permite
     * mandar órdenes al servidor para obtener o enviar ficheros, cambiar el modo de envío...
     * Antes de poder enviar o recibir paquetes, se le pedirá al usuario que introduzca tanto la IP
     * como el PUERTO del servidor (el puerto se muestra en la interfaz del servidor)
     */
    private static void startCommandLine() throws IOException {
        //leemos IP y PUERTO
        System.out.print(" > Introduzca IP del servidor: ");
        serverIP = menu.nextParameter();

        System.out.print(" > Introduzca PUERTO de conexión: ");
        try {
            serverPort = Integer.parseInt(menu.nextParameter());
            //creamos dirección del servidor
            serverAddress = InetAddress.getByName(serverIP);
            //abrimos socket
            socket = new DatagramSocket();
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("El puerto es de tipo NUM [0-65535]");
        }

        //iniciamos la línea de comandos
        System.out.println(" -- Use \"<HELP|help>\" para ver ayuda --");
        System.out.println("pcXX% Cliente TFTP [" + InetAddress.getLocalHost() + "]");
        String command = menu.nextAction();
        while (!command.equals("quit")) {
            receiveMss = new HashMap<>();
            tid = new TID<Integer, Integer>(socket.getLocalPort(), serverPort);
            switch (command) {
                case ("mode octet"):
                    mode = "octet";
                    System.out.println(" > Modo cambiando a \"octet\"");
                    break;
                case ("mode netascii"):
                    mode = "netascii";
                    System.out.println(" > Modo cambiado a \"netascii\"");
                    break;
                case ("help"):
                case ("HELP"):
                    menu.commands();
                    break;
                default:
                    EOF = false;
                    if (Pattern.matches(putTransfer, command)) {
                        String fileName = command.replaceAll(put_head, "");
                        if (Pattern.matches(filePatron, fileName)) {
                            System.out.println("Subiendo " + fileName + " ... ");
                            //enviamos establecimiento
                            Packet wrq = factory.newPacket("WRQ", mode, fileName, "", "");
                            send(wrq);
                            sendFile(fileName);
                        } else {
                            System.out.println("> El nombre del fichero no es válido");
                        }
                    } else if (Pattern.matches(getTransfer, command)) {
                        String fileName = command.replaceAll(get_head, "");
                        if (Pattern.matches(filePatron, fileName)) {
                            System.out.println("Descargando " + fileName + " ... ");
                            //enviamos establecimiento
                            Packet rrq = factory.newPacket("RRQ", mode, fileName, "", "");
                            send(rrq);
                            receiveFile();
                            if (!receiveMss.isEmpty()) {
                                saveMessage(rrq.getFileName());
                            }
                        } else {
                            System.out.println("> El nombre del fichero no es válido");
                        }
                    } else if (Pattern.matches(connect_patron, command)) {
                        hostName = command.replaceAll(connect_head, "");
                        System.out.println(" > Nombre del servidor registrado como \"" + hostName + "\"");
                        connect = true;
                    }
            }
            command = menu.nextAction();
            LOST_PACKETS = 0;
            RETR_PACKETS = 0;
            SEND_PACKETS = 0;
        }
    }

    //=============================================
    //MÉTODOS PARA EL ENVÍO Y RECEPCIÓN DE FICHEROS
    //=============================================
    private static void receiveFile() throws IOException {
        boolean first_data_received = false;
        boolean end = false;
        int n_sec = 1;
        String last_ack = str(n_sec);
        Packet packet = null;
        //repetimos mientras que nos se haya enviado el último paquete de confirmación
        while (!end) {
            int tries = 0;
            boolean receivedReponse = false;

            do {
                if (first_data_received) {
                    //mandamos ack
                    Random r = new Random();
                    if (r.nextInt(100) >= 5) {
                        if (last_ack.equals(str(n_sec))) {
                            Packet ack = factory.newPacket(Packet.ACK, "", "", str(n_sec), "");
                            send(ack);
                            n_sec++;
                            SEND_PACKETS++;

                            if (EOF) {
                                end = true;
                                receivedReponse = true;
                            }
                        }
                    }else {
                        LOST_PACKETS++;
                    }
                }
                socket.setSoTimeout(TIMEOUT);


                //intentamos recibir paquete
                try {
                    if (!EOF) {
                        packet = receive();
                        if (packet != null && packet.getHEAD().equals(Packet.DATA_HEAD)) {
                                receivedReponse = true;
                                first_data_received = true;
                                receiveMss.put(packet.getAck(), packet.getMessage());
                                last_ack = packet.getAck();
                        }
                        if (packet != null && packet.getHEAD().equals(Packet.ERROR_HEAD)) {
                            System.out.println(packet.getMessage());
                            receivedReponse = true;
                            end = true;
                            EOF = true;
                        }
                    }
                } catch (SocketTimeoutException st) {
                    tries++;
                    RETR_PACKETS++;
                }
            } while (tries < MAXTRIES && !receivedReponse && !EOF);


                socket.setSoTimeout(0);


            if (packet != null && !end && packet.size() < Packet.ECHOMAX) {
                EOF = true;
            }
        }

        if (packet != null && !packet.getHEAD().equals(Packet.ERROR_HEAD)) {
            System.out.println("P.Enviados: " + SEND_PACKETS + ", P.Perdidos: " + LOST_PACKETS + ", P.Retr: " + RETR_PACKETS);
        }

    }

    private static void sendFile(String fileName) throws IOException {
        //tratamos de abrir el fichero
        boolean error = false;
        try (FileReader fR = new FileReader(fileName)) {
            List<String> data_list = openAndRead(fR);
            //recorremos esta lista de datos y empezamos a enviarlos
            //recibimos primer ack
            Packet ackPacket = receive();
            int n_sec = 0;
            n_sec++;
            for (String data : data_list) {
                int tries = 0;
                boolean receivedResponse = false;
                do {
                    Random r = new Random();
                    if (r.nextInt(100) >= 5) {
                        //enviamos dato
                        Packet dataPacket = factory.newPacket(Packet.DATA, "", "", str(n_sec), data);
                        send(dataPacket);
                        SEND_PACKETS++;
                    } else {
                        LOST_PACKETS++;
                    }
                    socket.setSoTimeout(TIMEOUT);

                    try {
                        //esperamos la respuesta de un ack
                        ackPacket = receive();
                        receivedResponse = true;
                        n_sec++;

                    } catch (SocketTimeoutException e) {
                        tries++;
                        RETR_PACKETS++;
                    }
                } while (tries < MAXTRIES && !receivedResponse);
                //Reestablecemos timeout
                if (receivedResponse) {
                    socket.setSoTimeout(0);
                }
            }

            System.out.println("P.Enviados: " + SEND_PACKETS + ", P.Perdidos: " + LOST_PACKETS + ", P.Retr: " + RETR_PACKETS);

        } catch (FileNotFoundException fn) {
            //recibimos el ack que este nos manda, y luego le mandamos el error
            Packet ack = receive();
            Packet err = factory.newPacket(Packet.ERROR, "", "", "", "ERROR. El fichero no existe");
            send(err);
            System.out.println(err.getMessage());
        }
    }

    //==============================================
    //METODOS PARA EL ENVÍO Y RECEPCIÓN DE PAQUETES
    //==============================================

    /**
     * Método utilizado para el envío de un paquete
     *
     * @param packet Paquete a enviar
     * @throws IOException lanzado en caso de error en el envío (buffer vacío, buffer null, ...)
     */
    private static void send(Packet packet) throws IOException {
        sendPacket = new DatagramPacket(packet.toBytes(), packet.size(), serverAddress, serverPort);
        socket.send(sendPacket);
    }

    /**
     * Método utilizado para la recepción de paquetes.
     *
     * @return Paquete recibido
     * @throws IOException lanzado en caso de cualquier tipo de error por el paquete recibido
     */
    private static Packet receive() throws IOException {
        receivePacket = new DatagramPacket(new byte[Packet.ECHOMAX], Packet.ECHOMAX);
        socket.receive(receivePacket);
        //creamos un tid auxiliar
        int server_port = receivePacket.getPort();
        TID<Integer, Integer> tid_aux = new TID<Integer, Integer>(socket.getLocalPort(), server_port);
        if (tid.equals(tid_aux)) {
            return factory.newPacket(receivePacket.getData(), receivePacket.getLength());
        } else {
            return null;
        }

    }

    //=============
    //OTROS MÉTODOS
    //=============

    /**
     * Método que convierte un número de secuencia desde "int" a "String", para que
     * este ocupe 2 bytes. Se utiliza para añadir el numero de secuencia o de bloque en los
     * paquetes que utilizan este campo
     *
     * @param ack Numero de secuencia (int)
     * @return Numero de Secuencia (String)
     */
    private static String str(int ack) {
        if (ack < 10) {
            return "0" + (ack);
        } else {
            return String.valueOf(ack);
        }
    }

    /**
     * Método utilizado para extraer de un fichero todos los datos en bloques de ECHOMAX - 4 bytes.
     * Estos 4 bytes se corresponden al tamaño de la cabecera más el número de bloque. En este caso, ECHOMAX
     * tiene un valor de 512, luego como máximo se envian 508 bytes de datos
     *
     * @param fR FileReader utilizado en la apertura del fichero
     * @return Lista de mensajes a enviar
     * @throws IOException lanzado en caso de error en la lectura del fichero
     */
    private static List<String> openAndRead(FileReader fR) throws IOException {
        List<String> sol = new ArrayList<>();
        int cont = 0;
        String aux = "";
        boolean end = false;
        int c = -2;
        while (!end) {
            if (cont == 508 || c == -1) {
                sol.add(aux);
                aux = "";
                cont = 0;
                if (c == -1) {
                    end = true;
                }
            } else {
                c = fR.read();
                if (c != -1) {
                    cont++;
                    aux += (char) c;
                }
            }
        }
        return sol;
    }

    /**
     * Método que se encarga de guardar en un fichero de texto el fichero descargado del servidor
     * @param fileName Nombre del fichero
     */
   private static void saveMessage (String fileName){
        File file = new File(fileName);
       PrintWriter pw = null;
       try {
            pw = new PrintWriter(file);
       }catch (FileNotFoundException f){
           f.getStackTrace();
       }
       //escribimos
       int n_sec = 1;
       for (int i = 1; i <=receiveMss.size();i++){
           String block_number = str(i);
           pw.print(receiveMss.get(block_number));
       }
       pw.close();
   }
}
