import java.io.*;
import instructions.*;
import java.util.*;

public class SwiftPL {

	public static PrintStream out;
	public static TS symbolTable;
	
	public static void main (String args[] ) {
		List<Instruction> instructions = null;
		
		try {
			TS symbolTable = new TS();
			Reader in = new InputStreamReader(System.in);
			out = System.out;
			if (args.length > 0 ){
				in = new FileReader(args[0]);
			}

			if (args.length > 1 ){
				out = new PrintStream(new FileOutputStream(args[1]));
			}

			System.setOut(out);
			System.setErr(out);
			parser p = new parser(new Yylex(in));
			p.parse();

			instructions = p.getAST();
			//inicialización de la tabla de símbolos
			//ejecutamos las instrucciones
			for (Instruction i : instructions){
				if (i!=null){
					i.run(symbolTable);
					/*
					System.out.println("\n<-------------------------------------------");
					System.out.println(i);
					symbolTable.showVariables();
					System.out.println("------------------------------------------->\n");
					*/
				}
			}
			symbolTable.showVariables();
		}catch (Exception e){
			e.printStackTrace();
			//TS.showTS();
			//System.exit(0);
			//symbolTable.showVariables();
		}
		//TS.showVariables();
	}

}
