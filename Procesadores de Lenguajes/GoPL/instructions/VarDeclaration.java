package instructions;

import java.util.*;

public class VarDeclaration implements Instruction {

	private String varName;
	private Object value;
	private String valueType;
	private String varType;


//declaración de una variable a un valor
	public VarDeclaration (String varName, String varType, Object value){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.valueType = Global.typeOf(value);
	}


	//declaración de una variable a un valor inicial
	public VarDeclaration (String varName, String varType, Object value, String valueType){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.valueType = valueType;
	}


	//declaración multiple de variable sin valor inicial
	private List<String> varNames;
	public VarDeclaration (List<String> varNames, String varType, String valueType){
		this.varNames = varNames;
		this.varType = varType;
		this.valueType = valueType;
	}

	//declaración de varias variables a un valor concreto indicando el tipo
	//var a,b,c int = 1,2,3
	private List values_list;
	public VarDeclaration (List<String> varNames, String varType, String valueType, List values_list){
		this.varNames = varNames;
		this.varType = varType;
		this.valueType = valueType;
		this.values_list = values_list;
	}

	//declaración de variables múltiple, asignando a cada variable un valor diferente
	public VarDeclaration (List<String> varNames, String varType, List<String> values_list){
		this.varNames = varNames;
		this.varType = varType;
		this.values_list = values_list;
	}

	@Override
	public Object run (TS symbolTable){
		//declaración de variables múltiples (var a,b,c:[tipo]) sin asignación de valor
		if (varNames!=null){
			if (values_list != null){
							
				if (values_list.size() == 0){
					var_cero_declaration(symbolTable);
					return null;
				}
				if (varNames.size() != values_list.size()){
					Err err = new Err("ERROR");
					err.run(symbolTable);
				}
			}else{
				this.values_list = new ArrayList<>();
			}
			for (int i=0;i<varNames.size(); i++){
				
				String n = varNames.get(i);
				Var v = null;
				String current_value_type = null;
				
				if (values_list!=null){
					Operation val = (Operation)values_list.get(i);
					Object val_run = val.run(symbolTable);
					//checkear el valor de la variable que vamos a declarar
					if (valueType==null){
						current_value_type = Global.typeOf(val_run);
					}else{
						current_value_type = valueType;
					}
					if (!Global.typeOf(val_run).equals(current_value_type)){
						Err err = new Err("ERROR");
						err.run(symbolTable);
					}
					v = new Var(n, varType, val_run);
				}else {
					v = new Var(n, varType, valueType);
				} 
				if (symbolTable.isDefined(v)){
					String msg = "error (variable ya definida)";
					Err err =  new Err(msg);
					err.run(symbolTable);
				}

				if (value!=null && this.valueType != null){
					v.castValue(valueType);
				}

				symbolTable.addVar(v);
			}

		//declaración de variables simple (var a:[tipo] = [expresion]) con o sin tipo
		//y con o sin asignación de valor
		}else{
			Var v = null;
			if (value!=null){
				Operation op = (Operation)value;
				v = new Var(varName, varType, op.run(symbolTable));
			}else{
				v = new Var(varName, varType, valueType);
			}

			if (symbolTable.isDefined(v)){
				String msg = "error (variable ya definida)";
				Err err =  new Err(msg);
				err.run(symbolTable);
			}

			if (value!=null && this.valueType != null){
				v.castValue(valueType);
			}

			symbolTable.addVar(v);
		}
		return null;
	}

	private void var_cero_declaration (TS symbolTable){
		for (String n : varNames){
			if (valueType == null){
				Err err = new Err("ERROR");
				err.run(symbolTable);
			}
			Var v = new Var(n, Global.VARIA, valueType);
			if (symbolTable.isDefined(v)){
				Err err = new Err("ERROR");
				err.run(symbolTable);
			}
			symbolTable.addVar(v);
		}
	}
}