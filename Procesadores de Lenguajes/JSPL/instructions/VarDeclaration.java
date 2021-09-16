package instructions;

import java.util.*;

public class VarDeclaration implements Instruction {


	private String valueType;
	private String varType;


	private List<String> varNames;
	public VarDeclaration (List<String> varNames, String varType, String valueType){
		this.varNames = varNames;
		this.varType = varType;
		this.valueType = valueType;
	}

	/* Constructor para variables sin inicializar	*/
	public VarDeclaration (List<String> varNames, String varType){
		this.varNames = varNames;
		this.varType = varType;
		this.valueType = Global.UNDEFINED;
	}

	/* Constructor para inicializar varias variables a un mismo valor (dependiendo de la longitud de values)	*/
	private List values;
	public VarDeclaration (List<String> varNames, String varType, List values){
		this.varNames = varNames;
		this.varType = varType;
		this.values = values;
	}

	@Override
	public Object run (TS symbolTable){
		//declaracion de variables
		//var a,b,c = 1 -> asignacion de las tres variables el valor 1
		//var a,b,c = 1,2,3 -> asignacion de a=1, b=2, c=3
		//var a,b,c = 1,2   -> error
		if (varNames != null){
			if (values == null){
				for (String varName : varNames){
					Var v = symbolTable.getVar(varName);
					if (v != null){
						//variable ya definida en el bloque actual
						Err err = new Err("error (variable " + varName + " ya definida)");
						err.run(symbolTable);
						return err;
					}
					v = new Var(varName, varType,  Global.UNDEFINED);
					symbolTable.addVar(v);
				}
			}else{
				//miramos la longitud de los valores
				if (values.size() == 1){
					//inicializamos todas las variables al valor correspondiente
					Operation o = (Operation)values.get(0);
					for (String varName: varNames){
						Var v = symbolTable.getVar(varName);
						if (v!=null){
							Err err = new Err("error (variable " + varName + " ya definida)");
							err.run(symbolTable);
							return err;
						}
						v = new Var(varName, varType, o.run(symbolTable));
						symbolTable.addVar(v);
					}
				}else {
					//miramos si la longitud de ambos arrays (variables y valores) coinciden
					//si no es error
					if (values.size() == varNames.size()){
						for (int i =0 ;i<varNames.size(); i++){
							String varName = varNames.get(i);
							Operation o = (Operation)values.get(i);
							Var v = symbolTable.getVar(varName);
							if (v != null){
								Err err = new Err("error (variable " + varName + " ya definida)");
								err.run(symbolTable);
								return err;
							}
							v = new Var(varName, varType, o.run(symbolTable));
							symbolTable.addVar(v);
						}
					}else{
						Err err = new Err("error (error en la declaracion de variables)");
						err.run(symbolTable);
						return err;
					}
				}
			}
		}

		return null;
	}

}