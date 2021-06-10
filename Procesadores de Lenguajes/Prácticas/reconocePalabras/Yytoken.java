public class Yytoken {
    public final static int ESPACIO = 0; 
    public final static int TERMINA_VOCAL = 1;
    public final static int DOBLE_VOCAL = 2;
    public final static int DOBLE_Y_TERMINA = 3;
    public final static int OTROS = 190;
    private int token;
    private int valor;

    public Yytoken(int token, int valor) {
         this.token = token;
         this.valor = valor;
    }
    public Yytoken(int token, String lexema) {
         this(token, Integer.parseInt(lexema));
    }

    public int getToken()  {
         return token;
    }

    public int getValor() {
         return valor;
    }

    public String toString() {
         return "<"+token+","+valor+">";
    }
}
