package instructions;

import java.util.*;

public class Function implements Instruction{

    private String name;
    private List params;
    private List<Instruction> ins;
    

    public Function (String name, List params, List<Instruction> ins){
        this.name = name;
        this.params = params;
        this.ins = ins;
    }

    public List get_params(){
        return this.params;
    }

    public List get_ins(){
        return this.ins;
    }

    @Override
    public Object run (TS symbolTable){
        //miramos si ya hay una funcion con ese nombre
        if (symbolTable.functions.containsKey(name)){
            Err err = new Err("error (funcion '" + name + "' ya ha sido definida)");
            err.run(symbolTable);
            return err;
        }

        symbolTable.functions.put(name, this);

        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (params != null){
            sb.append(params.toString());
        }
        sb.append(",");
        sb.append("\n\t");
        sb.append(ins.toString());
        sb.append("]");
        return sb.toString();
    }

}