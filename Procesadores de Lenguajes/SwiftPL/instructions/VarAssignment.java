package instructions;

import java.util.*;

/**
 *	Clase utilizada para asignar el valor a una variable ya declarada 
*/
public class VarAssignment implements Instruction {

	private String varName;
	private Object value;

	/**
	* Asignación de un nuevo valor a una variable existente
	* @varName nombre de la variable
	* @value   nuevo valor
	*/
	public VarAssignment (String varName, Object value){
		this.varName = varName;
		this.value = value;
	}

	/**	
	* Reasignación de valor una posición de un array 
	* @varName nombre de la variable
	* @index   índice del array
	* @value   nuevo valor
	*/
	private Operation index;
	public VarAssignment (String varName, Operation index, Object value){
		this.varName = varName;
		this.index = index;
		this.value = value;
	}


	@Override
	public Object run(TS symbolTable){
		Var v = symbolTable.getVar(varName);
		boolean wasNull = v.getValue() == null;

		if (v==null){
			Err err = new Err("error (variable no declarada)");
			err.run(symbolTable);
		}

		if (v.getVarType().equals(Global.CONST) && v.getValue()!=null){
			Err err = new Err("error (no se puede modificar una constante)");
			err.run(symbolTable);
		}

		Operation o = (Operation)value;

		if (index == null){//no es un array
			symbolTable.updateVar(varName, o.run(symbolTable));
		}else{
			List l = (List)v.getValue();
			Integer i = (Integer)index.run(symbolTable);
			l.set(i, o.run(symbolTable));
		}
		

		//cast en el caso de que la variable  no hubiese sido inicializada y
		//el tipo de esta se hubiese predefinido
		if (wasNull){
			v.castValue(v.getType());
		}

		return null;
	}
}