import Packages.Packet;
import Packages.PacketFactory;
import TID.TID;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * SERVIDOR TFPT ITERATIVO
 * David Gómez Pérez
 */
public class TFTPServer {

    /*  CONTROL DE TIMEOUT */
    private static final int MAXTRIES = 5;
    private static final int TIMEOUT = 1000;

    /* SOCKETS Y BUFFERS */
    private static DatagramSocket socket;
    private static DatagramPacket receivePacket;
    private static DatagramPacket sendPacket;
    private static int serverPort;

    /* CLIENTE  */
    private static InetAddress clientAddress;
    private static int clientPort;

    /* TID PARA COMPROBACIONES */
    private static TID<Integer, Integer> tid;

    /* MAP PARA GUARDAR MENSAJES */
    private static Map<String, String> receiveMss;

    /* FACTORY PARA LA CREACIÓN DE PAQUETES */
    private static PacketFactory factory;

    /* BOOLEANO FIN DE FICHERO */
    private static boolean EOF = false;

    /* MODO DE TRANSMISIÓN DE FICHERO */
    private static String mode = "octet";

    /* BOOLEANO QUE INDICA SI SE HA RECIBIDO RESPUESTA */
    private static boolean requestReceived = false;

    public static void main(String[] args) throws IOException {
        factory = new PacketFactory();
        Random nextPort = new Random();
        while ((serverPort = nextPort.nextInt(65536)) == 69) {
        }
        //abrimos socket
        socket = new DatagramSocket(serverPort);
        System.out.println("CONEXIÓN INICIADA EN [<" + InetAddress.getLocalHost() + "," + serverPort + ">]");
        start();
    }

    //================
    //METODO PRINCIPAL
    //================
    private static void start() throws IOException {
        while (true) {
            //recibimos petición
            Packet request = receive();
            receiveMss = new HashMap<>();
            if (request != null) {
                EOF = false;
                System.out.println("Nueva petición de [<" + clientAddress + "," + clientPort + ">]");
                //miramos el tipo de petición recibida
                if (request.getHEAD().equals(Packet.RRQ_HEAD)) {
                    //enviamos fichero
                    System.out.println("===============\n| TRANSACCIÓN |\n===============");
                    System.out.println("            <----- RRQ \"" + mode + "\" \"" + request.getFileName() + "\"");
                    sendFile(request.getFileName());
                } else if (request.getHEAD().equals(Packet.WRQ_HEAD)) {
                    //recibimos fichero
                    System.out.println("===============\n| TRANSACCIÓN |\n===============");
                    System.out.println("            <----- WRQ \"" + mode + "\" \"" + request.getFileName() + "\"");
                    receiveFile();
                    if (!receiveMss.isEmpty()) {
                        saveMessage(request.getFileName());
                    }
                } else {
                    System.out.println("Petición no válida");
                }
                requestReceived = false;
            }
        }
    }

    //=============================================
    //MÉTODOS PARA EL ENVÍO Y RECEPCIÓN DE FICHEROS
    //=============================================
    private static void receiveFile() throws IOException {
        boolean end = false;
        int n_sec = 0;
        String last_ack = str(n_sec);
        Packet packet = null;
        while (!end) {
            int tries = 0;
            boolean receiveResponse = false;
            do {
                //intentamos enviar el primer ack
                Random r = new Random();
                if (r.nextInt(100) >= 5 || n_sec == 0) {
                    if (last_ack.equals(str(n_sec))) {
                        Packet ack = factory.newPacket(Packet.ACK, "", "", str(n_sec), "");
                        send(ack);
                        System.out.println("ACK " + str(n_sec) + " ----->");
                        if (EOF) {
                            end = true;
                            receiveResponse = true;
                        }
                        n_sec++;
                    }
                } else {
                    System.out.println("ACK (P)" + str(n_sec) + " ----->");
                }
                socket.setSoTimeout(TIMEOUT);

                try {
                    if (!EOF) {
                        packet = receive();
                        if (packet != null && packet.getHEAD().equals(Packet.DATA_HEAD)) {
                            receiveResponse = true;
                            receiveMss.put(packet.getAck(), packet.getMessage());
                            if (tries==0) {
                                System.out.println("                 <----- DATA " + packet.getAck() + " (" + packet.size() + ")");
                            }else{
                                System.out.println("                 <----- DATA (R)" + packet.getAck() + " (" + packet.size() + ")");
                            }
                            last_ack = packet.getAck();
                        }
                        if (packet != null && packet.getHEAD().equals(Packet.ERROR_HEAD)) {
                            System.out.println(packet.getMessage());
                            receiveResponse = true;
                            end = true;
                            EOF = true;
                        }
                    }
                } catch (SocketTimeoutException s) {
                    System.out.println("                 <----- DATA (R)" + packet.getAck());
                    tries++;
                }

            } while (tries < MAXTRIES && !receiveResponse && !EOF);

            if (receiveResponse) {
                socket.setSoTimeout(0);
            }

            if (packet != null && !end && packet.size() < Packet.ECHOMAX) {
                EOF = true;
            }
        }
    }


    /**
     * Método que envía un fichero
     *
     * @param fileName nombre del fichero a enviar
     */
    private static void sendFile(String fileName) throws IOException {
        //tratamos de abrir el fichero
        try (FileReader fR = new FileReader(fileName)) {
            List<String> data_list = openAndRead(fR);
            //recorremos esta lista de datos y empezamos a enviarlos
            int n_sec = 1;
            for (String data : data_list) {
                int tries = 0;
                boolean receivedResponse = false;
                do {
                    Random r = new Random();
                    if (r.nextInt(100) >= 5) {
                        //enviamos dato
                        Packet dataPacket = factory.newPacket(Packet.DATA, "", "", str(n_sec), data);
                        send(dataPacket);
                        if (tries == 0) {
                            System.out.println("DATA " + str(n_sec) + " (" + dataPacket.size() + ") ----->");
                        } else {
                            System.out.println("DATA " + str(n_sec) + " (R) (" + dataPacket.size() + ") ----->");
                        }
                    } else {
                        System.out.println("DATA (P)" + str(n_sec) + " ----->");
                    }
                    socket.setSoTimeout(TIMEOUT);
                    try {
                        //esperamos la respuesta de un ack
                        Packet ackPacket = receive();
                        if (tries == 0) {
                            System.out.println("            <----- ACK " + str(n_sec));
                        } else {
                            System.out.println("            <----- ACK (R)" + str(n_sec));
                        }
                        receivedResponse = true;
                        n_sec++;
                    } catch (SocketTimeoutException s) {

                        if (n_sec > 1) {
                            System.out.println("            <----- ACK (R)" + str(n_sec - 1));
                        } else {
                            System.out.println("            <----- ACK (R)" + str(n_sec));
                        }
                        tries++;
                    }
                } while (tries < MAXTRIES && !receivedResponse);
                if (receivedResponse) {
                    socket.setSoTimeout(0);
                }
                if (EOF) {
                    socket.setSoTimeout(0);
                }
            }
        } catch (FileNotFoundException fn) {
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
        sendPacket = new DatagramPacket(packet.toBytes(), packet.size(), clientAddress, clientPort);
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
        //si es el primer paquete que recibimos, obtenemos el puerto por el que este se comunica
        if (!requestReceived) {
            clientPort = receivePacket.getPort();
            clientAddress = receivePacket.getAddress();
            requestReceived = true;
            //creamos tid
            tid = new TID<>(clientPort, serverPort);
            System.out.println("TID para las transacciones: " + tid);
        }
        //creamos un tid auxiliar
        TID<Integer, Integer> tid_aux = new TID<Integer, Integer>(clientPort, serverPort);
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
