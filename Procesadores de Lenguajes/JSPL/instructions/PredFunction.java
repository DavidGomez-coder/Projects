package instructions;

import java.util.*;

public class PredFunction implements Instruction {

    private Object function;

    public PredFunction (Object function){
        this.function = function;
    }

    @Override 
    public Object run (TS symbolTable){
        if (function instanceof Length){
            Length l = (Length)function;
            String varName = l.get_var_name();
            Var v = symbolTable.getVar(varName);
            if (v == null){
                Err err = new Err("error (variable '" + v.getName() + "' no definida)");
                err.run(symbolTable);
                return err;
            }
            if (v.getType().equals(Global.STRING)){
                String val = v.getValue().toString();
                return val.length();
            }else if (v.getType().equals(Global.ARRAY)){
                List list = (List)v.getValue();
                return list.size();
            }else{
                Err err = new Err("error (funcion no definida para '" + function.getClass() + "')");
                err.run(symbolTable);
                return err;
            }
        }

        return null;
    }

}