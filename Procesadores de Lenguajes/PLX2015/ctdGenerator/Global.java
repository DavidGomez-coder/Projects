package ctdGenerator;

public class Global {

    //ULTIMO : 13

    //tipos de datos
    public static final String INTEGER = "INTEGER";
    public static final String FLOAT   = "FLOAT";
    public static final String LIST    = "LIST";
    public static final String ARRAY   = "ARRAY";
    public static final String TEMP    = "TEMP";
    //variables de bucles del tipo "nombre_variable[indice]"
    public static final String MUTABLE_LOOP_VAR = "MUTABLE_LOOP_VAR";

    //operadores aritmeticos
    public static final int PLUS   = 0;
    public static final int MINUS  = 1;
    public static final int MUL    = 2;
    public static final int DIV    = 3;
    public static final int MOD    = 13;

    //operadores de condicion
    public static final int EQ     = 4;
    public static final int NE     = 5;
    public static final int LT     = 6;
    public static final int LE     = 7;
    public static final int GT     = 8;
    public static final int GE     = 9;

    //operadores booleanos
    public static final int AND    = 10;
    public static final int OR     = 11;
    public static final int NOT    = 12;

    //tipo de incrementos
    public static final String LEFT    = "LEFT";
    public static final String RIGHT   = "RIGHT";

    //tipo de for-to
    public static final String TO     = "TO";
    public static final String DOWNTO = "DOWNTO";


    public static void error (String msg){
        System.out.println("error; #" + msg);
        System.out.println("halt;");
    }

    public static void halt (){
        System.out.println("halt;");
    }

    public static void gotoLabel (String l){
        System.out.println("\tgoto " + l + ";");
    }

    public static void label (String l){
        System.out.println(l + ":");
    }
}