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

	/* Valor unitario de un flotante /real*/
	public Operation (Double dd){
		this.value = dd;
		this.operator = Global.VALUE;
		this.operationType = Global.DOUBLE;
	}


	public Operation (String s){
		this.value = s;
		this.operator = Global.VALUE;
		this.operationType = Global.STRING;
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

	//ejecucion de funciones predefinidas
	private PredFunction func;
	public Operation (PredFunction func){
		this.func = func;
		this.operator = Global.FUNCTION;
	}

	private FunctionCall func2;
	public Operation (FunctionCall func){
		this.func2 = func;
		this.operator = Global.FUNCTION;
	}

	public boolean is_function_call (){
		return func2 != null;
	}

	//ejecucion del return
	private Return retExp;
	public Operation (Return retExp){
		this.retExp = retExp;
		this.operator = Global.RETURN;
	}

	public boolean is_return(){
		return this.retExp != null;
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

	/* Operaciones unarias*/
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

	@Override
	public Object run (TS symbolTable){
		if (this.operator.equals(Global.VALUE)){
			return singleValue(symbolTable);
		}

		if (this.operator.equals(Global.FUNCTION)){
			return functionExec(symbolTable);
		}

		if (this.operator.equals(Global.RETURN)){
			return retExp.run(symbolTable);
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
	* Este metodo se encarga de realizar las operaciones binarias dependiendo del tipo
	* de las operaciones pasadas como parametros. En caso de que alguno de los operandos
	* (izquierdo o derecho) sean nulos, es un error (solo se permite el caso de que el operando 
	* izquierdo sea nulo, y este se comprueba antes de llamar a este metodo y ademas es una 
	* operacion unaria)
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
			}else if (t1.equals(Global.STRING)){
				return stringBinaryOperation(symbolTable);
			}

		}else{
			if (t1.equals(Global.DOUBLE) || t2.equals(Global.DOUBLE)){
				Double d1 = new Double(leftOperation.run(symbolTable).toString());
				Double d2 = new Double(rightOperation.run(symbolTable).toString());
				Operation o = new Operation(new Operation(d1), this.operator, new Operation(d2));
				return o.run(symbolTable);
			}else if (t1.equals(Global.STRING) || t2.equals(Global.STRING)){
				String s1 = leftOperation.run(symbolTable).toString();
				String s2 = rightOperation.run(symbolTable).toString();
				Operation o = new Operation(new Operation(s1), this.operator, new Operation(s2));
				return o.run(symbolTable);
			}else{
				Err err = new Err("error (operacion con tipos no válidos)");
				err.run(symbolTable);
				return err;
			}
		}
		return null;
	}

	/*
	* Metodo utilizado para calcular el resultado de una expresion binaria de numeros enteros
	* Si se trata de una comparacion, el resultado es un booleano.
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
		}else if (this.operator.equals(Global.MOD)){
			return (Integer)leftOperation.run(symbolTable) % (Integer)rightOperation.run(symbolTable);
		}
		return null;
	}

	/*	
	* Metodo utilizado para calcular el resultado de las operaciones binarias sobre números reales (double)
	* En caso de que el operador sea de comparacion, el resultado es un booleano.
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
		}else if (this.operator.equals(Global.MOD)){
			return (Double)leftOperation.run(symbolTable) % (Double)rightOperation.run(symbolTable);
		}
		return null;
	}

	/**
	 * Metodo para operar sobre strings
	 */
	private Object stringBinaryOperation (TS symbolTable){
		if (this.operator.equals(Global.PLUS)){
			String i1 = leftOperation.run(symbolTable).toString();
			String i2 = rightOperation.run(symbolTable).toString();
			return (String)i1 + i2;
		}else if (this.operator.equals(Global.EQ)){
			String i1 = leftOperation.run(symbolTable).toString();
			String i2 = rightOperation.run(symbolTable).toString();
			return (Boolean)i1.equals(i2);
		}else if (this.operator.equals(Global.NE)){
			String i1 = leftOperation.run(symbolTable).toString();
			String i2 = rightOperation.run(symbolTable).toString();
			return !(Boolean)i1.equals(i2);
		}
		return null;
	}

	/*
	* Metodo que se utiliza para obtener el resuultado de una operacion binaria sobre 
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
	*	Devuelve un valor. Este metodo se invoca cuando se obtiene el resultado de una
	*	operacion. En caso, de que haya dos valores con distinto tipo en una misma operación
	*	se lanza un mensaje de error durante la ejecucion del mismo
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
					//Err err = new Err("error (variable no inicializada)");
					//err.run(symbolTable);
					return "undefined";
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
			//List i = (List) a.run(symbolTable);
			if (!sameValuesType(a.getList())){
				Err err = new Err("error (array con valores de distinto tipo)");
				err.run(symbolTable);
				return err;
			}
			a.setArrayType(Global.typeOf(a.getList().get(0)));
			return (List)a.run(symbolTable);
		}else if (this.operationType.equals(Global.STRING)){
			return this.value.toString();
		}else if (this.operationType.equals(Global.ARRAY_ACCESS)){
			System.out.println("#ENTRA");
		}

		return null;
	}

	//****************************************************************************			
	//					OPERACIONES UNARIAS
	//****************************************************************************
	/*
	* Este metodo se utiliza para realizar operaciones unarias. Es decir, cuando el 
	* operando izquierdo es nulo. Dependiendo del tipo de dato de este, se llama a uno 
	* de los metodos de abajo
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
	* Metodo para las operaciones unarias de tipo entero
	*/
	private Object integerUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.MINUS)){
			return (-1)*(Integer)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	/*
	* Metodo para las operaciones unarias de tipo real (double)
	*/
	private Object doubleUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.MINUS)){
			return (-1)*(Double)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	/*
	* Metodo para las operaciones unarias de tipo booleano 
	*/
	private Object boolUnitOperation (TS symbolTable){
		if (this.operator.equals(Global.NOT)){
			return !(Boolean)this.rightOperation.run(symbolTable);
		}
		return null;
	}

	//****************************************************************************			
	//					EJECUCION DE FUNCIONES
	//****************************************************************************
	private Object functionExec (TS symbolTable){
		if (func != null){
			return func.run(symbolTable);
		}else if (func2 != null){
			return func2.run(symbolTable);
		}

		return null;
	}

	//****************************************************************************			
	//					OPERACIONES SOBRE ARRAYS
	//****************************************************************************
	/*	Metodo definido sobre una lista que dice si sus valores son o no del mismo tipo*/
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