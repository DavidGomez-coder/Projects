package instructions;

import java.util.*;

/**
*	Clase utilizada para almacenar las variables (ya sean constantes o no)
*	, que luego se utilizarán en la tabla de símbolos
*/
public class Var {

	private String varName;
	private String varType;
	private Object value;
	private String type;


	/*	
		Constructor para variables con valor definido 
		@varName nombre de la variable
		@varType tipo de la variable (VARIABLE, CONSTANTE, ...)
		@value   valor asignado a la variable
	*/
	public Var (String varName, String varType, Object value){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.type = Global.typeOf(value);
	}

	/* 
		Constructor para variables sin valor definido
		@varName nombre de la variable
		@varType tipo de la variable (VARIABLE, CONSTANTE, ...)
		@type    tipo del valor de la variable
	*/
	public Var (String varName, String varType, String type){
		this.varName = varName;
		this.varType = varType;
		this.value = null;
		this.type = type;
	}

	public String getName(){
		return this.varName;
	}

	public String getVarType(){
		return this.varType;
	}

	public Object getValue(){
		return value;
	}

	public String getType(){
		return this.type;
	}

	public void setValue(Object value){
		this.value = value;
	}

	/*
		Este método se encarga de hacer un cast al valor de esta variable
		por otro indicado
		@type  tipo de datos al que hay que hacer casting al valor
	*/
	public void castValue(String type){
		if (Global.typeOf(value).equals(Global.DOUBLE) && type.equals(Global.INTEGER)){
			Double d = Double.parseDouble(this.value.toString());
			this.type = type;
			this.value = new Integer(d.intValue());
		}else if (Global.typeOf(value).equals(Global.INTEGER) && type.equals(Global.DOUBLE)){
			Integer i = Integer.parseInt(this.value.toString());
			this.type = type;
			this.value = new Double(i.doubleValue());
		}else if (Global.typeOf(value).equals(Global.ARRAY)){
			//obtenemos la lista de elementos sin hacer cast
			List list = (List)value;
			//hacemos casting
			List castList = new ArrayList<>();
			for (Object o : list){
				if (type.equals(Global.DOUBLE)){
					Integer i = Integer.parseInt(o.toString());
					castList.add(new Double(i.doubleValue()));
				}else if (type.equals(Global.INTEGER)){
					Double d = Double.parseDouble(o.toString());
					castList.add(new Integer(d.intValue()));
				}
			}
			this.value = castList;
		}

	}

	@Override
	public String toString(){
		return "VAR[" + varName + ", " + varType + ", " + value + ", " + type + "]";
	}


}