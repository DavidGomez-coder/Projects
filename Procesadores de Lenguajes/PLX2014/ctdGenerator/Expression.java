package ctdGenerator;

import java.util.*;

public class Expression {

    private String type;
    private Object value;


    public Expression (String type, Object value){
        this.type = type;
        this.value = value;
    }

    public String getType(){
        return this.type;
    }

    public Object getValue(){
        return this.value;
    }

    public void setValue(Object value){
        this.value = value;
    }

    public void setType (String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return "EXP{" + type + ", " + value.toString() + "}";
    }
}