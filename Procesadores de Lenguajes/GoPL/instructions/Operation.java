package instructions;

import java.util.*;

public class Operation implements Instruction{

	public Operation leftOperation;
	public Operation rightOperation;
	public String operationType;
	public String operator;
	public Object value;

	/* Valor unitario de un entero */
	public Operation (Integer in){
		this.value = in;
		this.operator = Global.VALUE;
		this.operationType = Global.INTEGER;
	}

	/* Valor unitario de un flotante real*/
	public Operation (Double dd){
		this.value = dd;
		this.operator = Global.VALUE;
		this.operationType = Global.DOUBLE;
	}

	/*	Valor unitario para booleanos */
	public Operation (Boolean b){
		this.value = b;
		this.operator = Global.VALUE;
		this.operationType = Global.BOOL;
	}

	/* Valor para arrays */
	public Operation (Array a){
		this.value = a;
		this.operator = Global.VALUE;
		this.operationType = Global.ARRAY;
	}

	/* Para strings	*/
	public Operation (String s){
		this.value = s;
		this.operator = Global.VALUE;
		this.operationType = Global.STRING;
	}

	private Operation arrayIndex;
	public Operation (String varName, Operation arrayIndex,  String operationType){
		this.value = varName;
		this.operator = Global.VALUE;
		this.operationType = operationType;
		this.arrayIndex = arrayIndex;
	}


	/* Operaciones binarias*/
	public Operation (Operation left, String operator, Operation right){
		this.leftOperation = left;
		this.rightOperation = right;
		this.operator = operator;
	}

	/* Operaciones unarias */
	public Operation (String operator, Operation right){
		this.rightOperation = right;
		this.operator = operator;
	}


	/* Acceso a variables */
	public Operation (String varName, String operationType){
		this.value = varName;
		this.operationType = operationType;
		this.operator = Global.VALUE;
	}

	private List varNames;
	public Operation (List varNames){
		this.value = varNames;
		this.operationType = Global.VARIABLE_PRINTS;
		this.operator = Global.VALUE;
	}

	

	@Override
	public Object run (TS symbolTable){

		if (this.operator.equals(Global.VALUE)){
			return singleValue(symbolTable);
		}

		//operaciones unarias
		if (this.leftOperation == null){
			return unaryOperation(symbolTable);
		}

		if (this.leftOperation!=null && this.rightOperation!=null){
			return binaryOperation(symbolTable);
		}
		
		return null;
	}

	//****************************************************************************			
	//					OPERACIONES BINARIAS
	//****************************************************************************

	/*
	* Este método se encarga de realizar las operaciones binarias dependiendo del tipo
	* de las operaciones pasadas como parámetros. En caso de que alguno de los operandos
	* (izquierdo o derecho) sean nulos, es un error (solo se permite el caso de que el operando 
	* izquierdo sea nulo, y este se comprueba antes de llamar a este método y además es una 
	* operación unaria)
	*/
	private Object binaryOperation (TS symbolTable){
		Object left = leftOperation.run(symbolTable);
		Object right = rightOperation.run(symbolTable);
		String t1 = Global.typeOf(left);
		String t2 = Global.typeOf(right);
		//System.out.println("TIPOS: " + t1 + ", " + t2);
		if (t1 == null || t2 == null){
			Err err = new Err ("error");
			err.run(symbolTable);
			return err;
		}
		if (t1.equals(t2)){ 
			if (t1.equals(Global.INTEGER)){
				return binaryIntegerOperation(symbolTable);
			}else if (t1.equals(Global.DOUBLE)){
				return doubleBinaryOperation(symbolTable);
			}else if (t1.equals(Global.BOOL)){
				return booleanBinaryOperation(symbolTable);
			}

		}else{
			Err err = new Err("error (operacion de tipos diferentes)");
			err.run(symbolTable);
			return err;
		}
		return null;
	}

	/*
	* Método utilizado para calcular el resultado de una expresión binaria de números enteros
	* Si se trata de una comparación, el resultado es un booleano.
	*/
	private Object binaryIntegerOperation (TS symbolTable){
		if (this.operator.equals(Global.PLUS)){
			return (Integer)leftOperation.run(symbolTable) + (Integer)rightOperation.run(symbolTable);
		}else if (this.operator.equals(Global.MINUS)){
			return (Integer)leftOperation.run(symbolTable) - (Integer)rightOperation.run(symbolTable);
		}else if (this.operator.equals(Global.MUL)){
			return (Integer)leftOperation.run(symbolTable) * (Integer)rightOperation.run(symbolTable);
		}else if (this.operator.equals(Global.DIV)){
			return (Integer)leftOperation.run(symbolTable) / (Integer)rightOperation.run(symbolTable);
		}else if (this.operator.equals(Global.LT)){
			return(Boolean)((int)leftOperation.run(symbolTable) < (int)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.LE)){
			return(Boolean)((int)leftOperation.run(symbolTable) <= (int)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.GT)){
			return(Boolean)((int)leftOperation.run(symbolTable) > (int)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.GE)){
			return(Boolean)((int)leftOperation.run(symbolTable) >= (int)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.EQ)){
			return(Boolean)((int)leftOperation.run(symbolTable) == (int)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.NE)){
			return(Boolean)((int)leftOperation.run(symbolTable) != (int)rightOperation.run(symbolTable));
		}
		return null;
	}

	/*	
	* Método utilizado para calcular el resultado de las operaciones binarias sobre números reales (double)
	* En caso de que el operador sea de comparación, el resultado es un booleano.
	*/
	private Object doubleBinaryOperation (TS symbolTable){
		if (this.operator.equals(Global.PLUS)){
			return ((Double)leftOperation.run(symbolTable) + (Double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.MINUS)){
			return ((Double)leftOperation.run(symbolTable) - (Double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.MUL)){
			return ((Double)leftOperation.run(symbolTable) * (Double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.DIV)){
			return ((Double)leftOperation.run(symbolTable) / (Double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.LT)){
			return(Boolean)((double)leftOperation.run(symbolTable) < (double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.LE)){
			return(Boolean)((double)leftOperation.run(symbolTable) <= (double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.GT)){
			return(Boolean)((double)leftOperation.run(symbolTable) > (double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.GE)){
			return(Boolean)((double)leftOperation.run(symbolTable) >= (double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.EQ)){
			return(Boolean)((double)leftOperation.run(symbolTable) == (double)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.NE)){
			return(Boolean)((double)leftOperation.run(symbolTable) != (double)rightOperation.run(symbolTable));
		}
		return null;
	}

	/*
	* Método que se utiliza para obtener el resuultado de una operación binaria sobre 
	* operandos binarios (operación booleanas)
	*/
	private Object booleanBinaryOperation (TS symbolTable){
		if (this.operator.equals(Global.OR)){
			return new Boolean((Boolean)leftOperation.run(symbolTable)) || new Boolean((Boolean)rightOperation.run(symbolTable));
		}else if (this.operator.equals(Global.AND)){
			return (Boolean)leftOperation.run(symbolTable) && (Boolean)rightOperation.run(symbolTable);
		}
		return null;
	}

	/*
	*	Devuelve un valor. Este método se invoca cuando se obtiene el resultado de una
	*	operación. En caso, de que haya dos valores con distinto tipo en una misma operación
	*	se lanza un mensaje de error durante la ejecución del mismo
	*/
	private Object singleValue(TS symbolTable){
		if ((this.operationType).equals(Global.INTEGER)){
			return Integer.parseInt(this.value.toString());
		}else if ((this.operationType).equals(Global.DOUBLE)){
			return Double.parseDouble(this.value.toString());
		}else if (this.operationType.equals(Global.VARIABLE)){
			if (this.arrayIndex == null){ //la variable no es un array
				//devolvemos el valor de la variable
				Var v = symbolTable.getVar((String)this.value);
				if (v==null){
					Err err = new Err("error (variable no declarada)");
					err.run(symbolTable);
					return err;
				}

				if (v.getValue() == null){
					Err err = new Err("error (variable no inicializada)");
					err.run(symbolTable);
					return err;
				}
				return v.getValue();
			}else { //accedemos a la posición de un array
				Var v = symbolTable.getVar((String)this.value);

				if (v==null){
					Err err = new Err("error (variable no declarada)");
					err.run(symbolTable);
					return err;
				}

				if (v.getValue() == null){
					Err err = new Err("error (variable no inicializada)");
					err.run(symbolTable);
					return err;
				}

				if (!v.getType().equals(Global.ARRAY)){
					Err err = new Err("error (la variable no es un array)");
					err.run(symbolTable);
					return err;
				}
				Integer i = (Integer)this.arrayIndex.run(symbolTable);
				List a = (List)v.getValue();
				if (i<0 || i>a.size()){
					Err err = new Err("error (fuera de rango)");
					err.run(symbolTable);
					return err;
				}
				return a.get(i);
			}
			
		}else if (this.operationType.equals(Global.BOOL)){
			return new Boolean (this.value.toString());
		}else if (this.operationType.equals(Global.ARRAY)){
			Array a = (Array)this.value;
			List i = (List) a.run(symbolTable);
			if (!sameValuesType(i)){
				Err err = new Err("error (array con valores de distinto tipo)");
				err.run(symbolTable);
				return err;
			}
			return i;
		}else if (this.operationType.equals(Global.STRING)){
			String a = (String)this.value;
			return value;
		}else if (this.operationType.equals(Global.VARIABLE_PRINTS)){
			List l = (List)this.value;
			return l;
		}


		return null;
	}

	//****************************************************************************			
	//					OPERACIONES UNARIAS
	//****************************************************************************

	/*
	* Este método se utiliza para realizar operaciones unarias. Es decir, cuando el 
	* operando izquierdo es nulo. Dependiendo del tipo de dato de este, se llama a uno 
	* de los métodos de abajo
	*/
	private Object unaryOperation (TS symbolTable){
		Object right = rightOperation.run(symbolTable);
		String type = Global.typeOf(right);

		if (type.equals(Global.INTEGER)){
			return integerUnitOperation(symbolTable);
		}else if (type.equals(Global.DOUBLE)){
			return doubleUnitOperation(symbolTable);
		}else if (type.equals(Global.BOOL)){
			return boolUnitOperation(symbolTable);
		}
		return null;
	}

	/*	
	* Método para las operaciones unarias de tipo entero
	*/
	private Object integerUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.MINUS)){
			return (-1)*(Integer)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	/*
	* Método para las operaciones unarias de tipo real (double)
	*/
	private Object doubleUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.MINUS)){
			return (-1)*(Double)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	/*
	* Método para las operaciones unarias de tipo booleano 
	*/
	private Object boolUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.NOT)){
			return !(Boolean)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	//****************************************************************************			
	//					OPERACIONES SOBRE ARRAYS
	//****************************************************************************
	/*	Método definido sobre una lista que dice si sus valores son o no del mismo tipo*/
	private boolean sameValuesType (List l){
		boolean ok = true;
		int i = 0;
		while (i<l.size() && ok){
			int j=i+1;
			while (j<l.size() && ok){
				String t1 = Global.typeOf(l.get(i));
				String t2 = Global.typeOf(l.get(j));
				if (!t1.equals(t2)){
					ok = false;
				}
				j++;
			}
			i++;
		}

		return ok;
	}

}