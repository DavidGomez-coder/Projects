package instructions;

import java.util.*;

/*
* Implementación de la sentencia WHILE
  Estructura:
  	WHILE condicion {
			instruciones
  	}		
*/
public class WhileIns implements Instruction{

	private Operation condition;
	private List<Instruction> sentences;

	/*
		Constructor bucle while
		@condition condición del bucle
		@sentences lista de sentencias del cuerpo del bucle
	*/
	public WhileIns (Operation condition, List<Instruction> sentences){
		this.condition = condition;
		this.sentences = sentences;
	}

	@Override
	public Object run(TS symbolTable){
		while ((Boolean)condition.run(symbolTable)){
			TS localSymbolTable = new TS();
			localSymbolTable.addAll(symbolTable.getVariables());
			for (Instruction i : sentences){
				if (i!=null){
					i.run(localSymbolTable);
				}
			}
		}
		return null;
	}
}