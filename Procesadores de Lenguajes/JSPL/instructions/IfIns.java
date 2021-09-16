package instructions;

import java.util.*;

/**
*	Implementacion de la sentencia de control IF-ELSE IF-ELSE
*   Estructura:
* 
* 	IF condicion {
* 		instrucciones
* 	}ELSE IF condicion {
* 		instrucciones
* 	}ELSE {
* 		instrucciones
* 	}
*/
public class IfIns implements Instruction {

	private Operation condition;
	private List<Instruction> listInstruction;
	private List<Instruction> elseIfInstruction;

	/* Constructor para la sentencia IF	*/
	public IfIns (Operation condition, List<Instruction> listInstruction){
		this.condition = condition;
		this.listInstruction = listInstruction;
	}

	/* Constructor para la sentencia ELSE*/
	public IfIns (List<Instruction> listInstruction){
		this.listInstruction = listInstruction;
		this.condition = new Operation((Boolean)true);
	}

	/* Constructor para las sentencias IF-ELSEIF*/
	public IfIns (Operation condition, List<Instruction> listInstruction, List<Instruction> elseIfInstruction){
		this.condition = condition;
		this.listInstruction = listInstruction;
		this.elseIfInstruction = elseIfInstruction;
	}

	@Override
	public Object run(TS symbolTable){
		if ((Boolean)condition.run(symbolTable)){
			TS localSymbolTable = new TS();
			localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
			for (Instruction i : listInstruction){
				if (i!=null){
					Object obj = i.run(localSymbolTable);
					if (obj!=null){
						return obj;
					}
				}
			}
		}else{
			if (elseIfInstruction!=null && elseIfInstruction.size()>0){
				TS localSymbolTable = new TS();
				localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
				for (Instruction i : elseIfInstruction){
					if (i!=null){
						Object obj = i.run(localSymbolTable);
						if (obj!=null){
							return obj;
						}
					}
				}
			}
		}

		return null;
	}
}