package ctdGenerator;

import java.util.*;

public class Var {

    private String varName;
    private Object value;
    private String type;

    public Var (String varName, String type, Object value){
        this.varName = varName;
        this.value = value;
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public String getName (){
        return this.varName;
    }

    public Object getValue (){
        return this.value;
    }

    public void setValue (Object value){
        this.value = value;
    }

    public void setType (String type){
        this.type = type;
    }

    @Override 
    public String toString(){
        return "VAR{" + varName + ", " + value.toString() + "}";
    }

    @Override
    public boolean equals (Object var){
        Var v = (Var)var;
        return this.varName.equals(v.getName());
    }

    @Override
    public int hashCode(){
        return this.varName.hashCode();
    }
}