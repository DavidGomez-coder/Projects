package instructions;

import java.util.*;

public class VarAssignment implements Instruction {

	private List<String> varNames;
	private List values;

	public VarAssignment (List<String> varNames, List values){
		this.varNames = varNames;
		this.values = values;
	}

	private Operation index;
	private String varName;
	private Operation value;
	public VarAssignment (String varName, Operation index, Operation value){
		this.varName = varName;
		this.index = index;
		this.value = value;
	}

	@Override
	public Object run(TS symbolTable){
		//a,b,c = 1 -> asignar a=b=c=1
		//a,b,c = 1,2,3 -> asignar a=1, b=2, c=3
		//a,b,c = 1,2	-> error
		//si alguna variable esta definida como constante, se lanza un error
		if (index != null){//asignacion a un array
			Var v = symbolTable.getVar(varName);
			if (!v.getType().equals(Global.ARRAY)){
				Err err = new Err("error (la variable '" + v.getName() + "' no es un array)");
				err.run(symbolTable);
				return err;
			}
			Integer i = (Integer)index.run(symbolTable);
			List l = (List)v.getValue();
			if (i<0 || i>=l.size()){
				Err err = new Err("error (acceso a una posicion no valida)");
				err.run(symbolTable);
				return err;
			}
			
			l.set(i, value.run(symbolTable));

			return null;
		}
		if (values.size() == 1){
			//asignamos a todas las variables el valor correspondiente a la posicion 0 del array
			Operation o = (Operation)values.get(0);
			for (String varName : varNames){
				Var v = symbolTable.getVar(varName);
				boolean wasNull = v.getValue() == null;

				if (v == null){
					Err err = new Err("error (variable " + v.getName() + " no declarada)");
					err.run(symbolTable);
					return err;
				}

				if (v.getVarType().equals(Global.CONST) && v.getValue()!=null){
					Err err = new Err("error (no se puede modificar una constante ya inicializada)");
				}

				//hacemos un update
				symbolTable.updateVar(varName, o.run(symbolTable));

				//cast en el caso de que la variable  no hubiese sido inicializada y
				//el tipo de esta se hubiese predefinido
				if (wasNull){
					v.castValue(v.getType());
				}
			}
		}else{
			//miramos si son de igual longitud,sino error
			if (values.size() == varNames.size()){
				for (int i=0; i<varNames.size(); i++){
					String varName = varNames.get(i);
					Operation o = (Operation)values.get(i);
					Var v = symbolTable.getVar(varName);
					boolean wasNull = v.getValue() == null;

					if (v == null){
						Err err = new Err("error (variable " + v.getName() + " no declarada)");
						err.run(symbolTable);
						return err;
					}
	
					if (v.getVarType().equals(Global.CONST) && v.getValue()!=null){
						Err err = new Err("error (no se puede modificar una constante ya inicializada)");
					}
					symbolTable.updateVar(varName, o.run(symbolTable));

					if (wasNull){
						v.castValue(v.getType());
					}
				}
			}else{
				Err err = new Err("error (longitud en la asignacion de variables)");
				err.run(symbolTable);
				return err;
			}
		}

		return null;
	}
}