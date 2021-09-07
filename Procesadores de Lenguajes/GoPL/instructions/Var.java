package instructions;

import java.util.*;

public class Var {

	private String varName;
	private String varType;
	private Object value;
	private String type;


	public Var (String varName, String varType, Object value){
		this.varName = varName;
		this.varType = varType;
		this.value = value;
		this.type = Global.typeOf(value);
	}

	public Var (String varName, String varType, String type){
		this.varName = varName;
		this.varType = varType;
		this.type = type;
		if (type.equals(Global.INTEGER)){
			this.value = new Integer(0);
		}else if (type.equals(Global.BOOL)){
			this.value = new Boolean(false);
		}else if (type.equals(Global.STRING)){
			this.value = "";
		}
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

	public void castValue(String type){
		if (Global.typeOf(value).equals(Global.DOUBLE) && type.equals(Global.INTEGER)){
			Double d = Double.parseDouble(this.value.toString());
			this.type = type;
			this.value = new Integer(d.intValue());
		}else if (Global.typeOf(value).equals(Global.INTEGER) && type.equals(Global.DOUBLE)){
			Integer i = Integer.parseInt(this.value.toString());
			this.type = type;
			this.value = new Double(i.doubleValue());
		}
	}

	@Override
	public String toString(){
		return "VAR[" + varName + ", " + varType + ", " + value + ", " + type + "]";
	}


}