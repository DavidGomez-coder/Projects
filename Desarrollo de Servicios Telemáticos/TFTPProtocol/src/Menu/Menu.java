package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * MENU
 * David Gómez Pérez
 */
public class Menu {
    private static String instrunctions = " ================================================================ \n" +
                                          "|                   Instrucciones Cliente TFTP                   |\n" +
                                          " ================================================================ \n" +
                                          "|   Comando   |       Argumento     |        Descripción         |\n" +
                                          " =============|=====================|============================ \n" +
                                          "|     mode    |   <octet|netascii>  |  Cambia modo de operacion  |\n" +
                                          "|     put     |   <nombre_fichero>  |  Sube al serv. un fich.    |\n" +
                                          "|     get     |   <nombre_fichero>  |  Recibe fichero del serv.  |\n" +
                                          "|   connect   |       <host>        |  Registra nombre del serv. |\n" +
                                          "|    quit     |                     |           Salir            |\n" +
                                          " ================================================================ \n";

    private static BufferedReader bf;

    public Menu(){
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    public void commands (){
        System.out.println(instrunctions);
    }

    public String nextParameter () throws IOException {
        System.out.print( " > ");
        return bf.readLine();
    }

    public String nextAction () throws IOException {
        System.out.print("tftp > ");
        return bf.readLine();
    }
}

