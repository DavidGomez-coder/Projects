package instructions;

import java.util.*;

public class FunctionCall implements Instruction {

    private String func_name;
    private List args;

    public FunctionCall (String func_name, List args){
        this.func_name = func_name;
        this.args = args;
    }

    @Override
    public Object run (TS symbolTable){
        //miramos si está definida o no la funcion
        if (!symbolTable.functions.containsKey(func_name)){
            Err err = new Err("error (la funcion '" + func_name + "' no ha sido declarada)");
            err.run(symbolTable);
            return err;
        }

        Function f = symbolTable.functions.get(func_name);
        TS local_symbol_table = new TS();
        local_symbol_table.addAll(symbolTable.getVariables(), symbolTable.functions);

        //inicializamos las variables inicializadas como parametros
        List f_params = f.get_params();
        List<Instruction> f_ins = f.get_ins();
        

        if (args!=null && f_params!=null){
            //control sobre el mismo numero de parametros
            if (f_params.size()!= args.size()){
                Err err = new Err("error (el numero de parametros en la funcion '" + func_name + "' no coinciden)");
                err.run(local_symbol_table);
                return err;
            }

            //declaracion de las variables
            for (int i = 0; i<args.size(); i++){
                Operation param_value = (Operation)args.get(i);
                String varName   = f_params.get(i).toString();
                Var v = new Var(varName, Global.VARIA, 
                                param_value.run(local_symbol_table));
                local_symbol_table.addVar(v);
                //System.out.println("#VARIABLE DE AMBITO DECLARADA: " + v);
            }
        }
        

        //ejecucion de las instrucciones de la funcion
        for (Instruction instruction : f_ins){
            if (instruction != null){
                Object obj = instruction.run(local_symbol_table);
                if (obj != null){
                    return obj;
                }
            }
        }


        return null;
    }
}