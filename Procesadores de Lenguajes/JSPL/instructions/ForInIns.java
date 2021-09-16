package instructions;

import java.util.*;

public class ForInIns implements Instruction {

    private String varInit; //no hace falta declararla (no debe de haberse declarado antes)
    private Object range;
    private List<Instruction> ins;

    public ForInIns (String varInit, Object range, List<Instruction> ins){
        this.varInit = varInit;
        this.range   = range;
        this.ins     = ins;
    }

    @Override
    public Object run (TS symbolTable){
        
        Var v = new Var(varInit, Global.VARIA, null);
        if (symbolTable.isDefined(v)){
            Err err = new Err("error (variable de control ya definida)");
            err.run(symbolTable);
            return err;
        }

        
        List list = null;
        
        if (range instanceof Array){
            Array ar = (Array)range;
            list = (List)ar.run(symbolTable);

        }else if (range instanceof String){ //nombre de una variable
            String varName = range.toString();
            Var v2 = symbolTable.getVar(varName);
            //System.out.println(v2);
            if (v2 == null){
                Err err = new Err("error (rango no definido)");
                err.run(symbolTable);
                return err;
            }

            if (v2.getValue() instanceof Array){
                Err err = new Err("error (el rango definido no corresponde a un array)");
                err.run(symbolTable);
                return err;
            }

            list = (List)v2.getValue();
        }
        


       //ejecucion
       for (Object o : list){
            TS localSymbolTable = new TS();
            localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
            localSymbolTable.addVar(v);
            localSymbolTable.updateVar(varInit, o);
            for (Instruction in : ins){
                if (in != null){
                    Object obj = in.run(localSymbolTable);
                    if (obj!=null){
                        return obj;
                    }
                }
            }
        }
        return null;
    }
}