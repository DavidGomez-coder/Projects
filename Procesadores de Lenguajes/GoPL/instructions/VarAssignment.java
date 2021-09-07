package instructions;

import java.util.*;

public class VarAssignment implements Instruction {

	private String varName;
	private Object value;

	public VarAssignment (String varName, Object value){
		this.varName = varName;
		this.value = value;
	}

	/*	Reasignación de una posición de un array */
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