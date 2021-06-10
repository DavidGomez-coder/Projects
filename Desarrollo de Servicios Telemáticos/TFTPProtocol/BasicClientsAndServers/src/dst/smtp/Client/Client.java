package dst.smtp.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** CLIENTE SMPT
 * -----------------------------------
 *  Autor: David Gómez Pérez
 *
 *  Se utiliza FAKE-SMTP como servidor
 *
 */
public class Client {

    /* COMANDOS PARA EL FUNCIONAMIENTO DEL PROTOCOLO */
    private static final String MAIL_FROM = "MAIL FROM: ";
    private static final String RCPT = "RCPT TO:";
    private static final String DATA = "DATA";
    private static final String HELO = "HELO";
    private static final String FROM = "From: ";
    private static final String TO = "To: ";
    private static final String QUIT = "QUIT";
    private static final String SUBJECT = "Subject: ";

    /* HOST DEL SERVIDOR SMPT  Y PUERTO DE CONEXIÓN*/
    private static final String SMPT_HOST = "127.0.0.1"; //utilizamos FAKE-SMTP como servidor de correo
    private static final int SMPT_PORT = 25;

    /* LISTA DE CORREOS ELECTRÓNICOS A LOS QUE ENVIAR EL MENSAJE */
    private static List<String> list_to_send;

    /* BUFFERS */
    private static BufferedReader cmd;
    private static BufferedReader in;
    private static PrintWriter out;


    public Client() {
        list_to_send = new ArrayList<>();
        cmd = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        try (Socket socket = new Socket(SMPT_HOST, SMPT_PORT)) {
            System.out.println("Estableciendo conexión con : " + SMPT_HOST);
            //buffers utilizado en para la lectura y la escritura de mensajes
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //respuesta del servidor al pedir la conexión
            String response = receive();
            //inicio de sesion
            send(HELO + " " + SMPT_HOST);
            //recibimos respuesta del servidor
            response = receive();

            //abrimos menu
            showMenu();

            //leemos comando
            System.out.print("SMPT$: ");
            String command = cmd.readLine();
            while (!command.equalsIgnoreCase(QUIT)){
                switch (command){
                    case "NEW" :
                        newMail();
                        break;

                    default:
                        System.out.println("    #ERROR. El comando introducido no es válido");
                }
                showMenu();
                System.out.print("SMPT$: ");
                command = cmd.readLine();
            }
            //hemos utilizado el comando QUIT, terminamos la conexión
            quitConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newMail () throws IOException {
        //introducimos direccion de correo que envía el mail y se lo enviamos al servidor
        System.out.print("          USER_MAIL$: MAIL FROM :");
        String userMail = cmd.readLine();
        send(MAIL_FROM + " <" + userMail+">");
        String response = receive();

        //introducimos los destinatarios
        System.out.println("          SMTP_MESS$: Introducir los destinatarios de ese correo. Usar '.' para terminar \n " +
                "         de introducir correos (Al menos un usuario) ");
        System.out.println();
        int count = 1;
        System.out.print("          DEST_MAIL$: Destinatario " + count + ": ");
        String dest = cmd.readLine();

        while (!dest.equals(".")){
            list_to_send.add("<"+dest+">");
            send(RCPT + " <" + dest + ">");
            count++;
            System.out.print("          DEST_MAIL$: Destinatario " + count + ": ");
            dest = cmd.readLine();
        }

        if (list_to_send.size() == 0){
            System.out.println("#ERROR. Se debe de introducir al menos un destinatario");

        }else{ //continuamos construyendo el correo
            String user_list = "";
            for (String str : list_to_send){
                user_list = user_list + " " + str;
            }
            //enviamos lista de usuarios
            response = receive();

            //empezamos a enviar datos
            send(DATA);
            response = receive();

            System.out.print(" > Subject: ");
            String subject = cmd.readLine();
            send(SUBJECT + " " + subject );
            send(FROM + " " + userMail );
            send(TO + " " + user_list );
            //enviamos linea en blanco para separar la cabecera del cuerpo del mensaje
            send("");


            System.out.println(" > Escribe el cuerpo del mensaje (linea en blanco para terminar) ");
            //escribimos datos
            String bodyMail = "";
            do {
                bodyMail = cmd.readLine();
                if (!bodyMail.equals("")){
                    send(bodyMail);
                }
            }while (!bodyMail.equals(""));
            send("");
            send(".");
            response = receive();


        }

    }

    private void quitConnection () throws IOException {
        out.println(QUIT);
        String response = receive();
    }


    private void showMenu (){
        System.out.println();
        System.out.println("·································································");
        System.out.println("·                               MENÚ                            ·");
        System.out.println("·································································");
        System.out.println("·     \"NEW\"   Enviar nuevo correo                               ·");
        System.out.println("·     \"QUIT\"  CERRAR                                            ·");
        System.out.println("·································································");
        System.out.println();
    }

    /* MÉTODOS PARA EL ENVÍO Y LA RECEPCIÓN DE DATOS */

    private void send (String msg){
        System.out.println(" > " + msg);
        out.println(msg);
    }

    private String receive () throws IOException {
        String readLine = in.readLine();
        System.out.println(" > " + readLine);
        return readLine;
    }


    public static void main(String[] args) {
        Client c = new Client();
        c.start();
    }

}
