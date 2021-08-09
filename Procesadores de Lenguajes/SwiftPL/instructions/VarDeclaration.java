package instructions;

import java.util.*;

/*
	Clase utilizada para la declaración de variables
*/
public class VarDeclaration implements Instruction {

	private String varName;
	private Object value;
	private String valueType;
	private String varType;


	/**
	 * Declaración de variables si tipo explicito
	 * @varName nombre de la variable
	 * @varType tipo de la variable (CONST, VARIABLE, ...)
	 * @value   valor de de la variable
	*/
	public VarDeclaration (String varName, String varType, Object value){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.valueType = Global.typeOf(value);
	}

	/**
	 * Declaración de variables con un tipo explícito
	 * @varName   nombre de la variable
	 * @varType   tipo de la variable (CONST, VARIABLE, ...)
	 * @value     valor de la variable
	 * @valueType tipo del valor de la variable (INTEGER, DOUBLE, BOOLEAN, ARRAY, ...)
	*/
	public VarDeclaration (String varName, String varType, Object value, String valueType){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.valueType = valueType;
	}


	private List<String> varNames;
	/**
	 * Declaración simultánea de varias variables sin valor predefinido (var a,b,c) 
	 * @varNames  lista con las variables a declarar
	 * @varType   tipo de la variable (CONST, VARIABLE, ...)
	 * @valueType tipo de valor de todas las variables (INTEGER, DOUBLE, BOOLEAN, ARRAY, ...)
	*/
	public VarDeclaration (List<String> varNames, String varType, String valueType){
		this.varNames = varNames;
		this.varType = varType;
		this.valueType = valueType;
	}


	@Override
	public Object run (TS symbolTable){
		//declaración de variables múltiples (var a,b,c:[tipo]) sin asignación de valor
		if (varNames!=null){
			for (String n : varNames){
				Var v = new Var(n, varType, valueType);
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

}