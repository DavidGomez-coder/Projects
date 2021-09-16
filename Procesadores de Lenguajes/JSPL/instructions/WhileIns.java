package instructions;

import java.util.*;

/*
* Implementacion de la sentencia WHILE
  Estructura:
  	WHILE condicion {
		instruciones
  	}		
*/
public class WhileIns implements Instruction{

	private Operation condition;
	private List<Instruction> sentences;

	public WhileIns (Operation condition, List<Instruction> sentences){
		this.condition = condition;
		this.sentences = sentences;
	}

	@Override
	public Object run(TS symbolTable){
		while ((Boolean)condition.run(symbolTable)){
			TS localSymbolTable = new TS();
			localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
			for (Instruction i : sentences){
				if (i!=null){
					Object obj = i.run(localSymbolTable);
					if (obj != null){
						return obj;
					}
				}
			}
		}
		return null;
	}
}