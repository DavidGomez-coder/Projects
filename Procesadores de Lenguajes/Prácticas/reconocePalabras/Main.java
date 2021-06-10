import java.io.*;
import java.io.IOException;
import java.util.*;
import java.*;

public class Main {
	private static int SISI = 0;
	private static int SINO = 0;
	private static int NOSI = 0;
	private static int NONO = 0;

	//
	private static List<String> palsSISI = new ArrayList<>();
	private static List<String> palsSINO = new ArrayList<>();
	private static List<String> palsNOSI = new ArrayList<>();
	private static List<String> palsNONO = new ArrayList<>();
	
	public static int getSISI(){
		return SISI;
	}

	public static int getSINO(){
		return SINO;
	}

	public static int getNOSI(){
		return NOSI;
	}

	public static int getNONO(){
		return NONO;
	}
	
	public static  void addSISI (String l) {
		SISI++;
		palsSISI.add(l);
	}	
	
	public static void addSINO (String l) {
		SINO++;
		palsSINO.add(l);
	}

	public static void addNOSI (String l) {
		NOSI++;
		palsNOSI.add(l);
	}

	public static void addNONO (String l) {
		NONO++;
		palsNONO.add(l);
	}

	public static void main (String[] args){
		if (args.length > 0) {
			System.out.println("-- ANALIZADOR DE PALABRAS --");
			try {
				Yylex lex = new Yylex(new FileReader(args[0]));
				Yytoken yytoken = null;
				while ( (yytoken = lex.yylex()) != null  ) {
					System.out.println(yytoken);
					if (yytoken.getToken() == Yytoken.DOBLE_Y_TERMINA)  {
                       addSISI(yytoken.toString());
                    }else  if (yytoken.getToken() == Yytoken.DOBLE_VOCAL) {
						addSINO(yytoken.toString());
                    }else  if (yytoken.getToken() == Yytoken.TERMINA_VOCAL) {
                       addNOSI(yytoken.toString());
					}else if (yytoken.getToken() == Yytoken.ESPACIO){

					}else{
						addNONO(yytoken.toString());
					}
				}
					System.out.println("<VOCALES_SEGUIDAS> <TERMINA_VOCAL> <NUM_PALABRAS>");
					System.out.println("        SI                 SI           "  + getSISI());
					System.out.println("        NO                 SI           "  + getNOSI());
					System.out.println("        SI                 NO           "  + getSINO());
					System.out.println("        NO                 NO           "  + getNONO());
			}catch (IOException e){
				System.err.println("Fichero no encontrado " + e.getStackTrace());
			}
		}
	}
}
