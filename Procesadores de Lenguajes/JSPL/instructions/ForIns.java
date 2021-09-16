package instructions;

import java.util.*;

public class ForIns implements Instruction {

    private Object varInit;
    private Operation condition;
    private Object varAct;
    private List<Instruction> ins;

    public ForIns (Object varInit, Operation condition, Object varAct, List<Instruction> ins){
        this.varInit = varInit;
        this.condition = condition;
        this.varAct = varAct;
        this.ins = ins;
    }

    @Override
    public Object run (TS symbolTable){

        Object initialization_exp = varInit;
        Object actualization_exp  = varAct;
        TS localSymbolTable = new TS();
        localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
        //declaracion de una variable para su inicializacion 
        if (initialization_exp != null){
            //declaramos la variable en el caso de que sea una declaracion
            //o actualizamos su valor
            if (initialization_exp instanceof VarDeclaration){
                VarDeclaration vd = (VarDeclaration)initialization_exp;
                vd.run(localSymbolTable);
            }else if (actualization_exp instanceof VarAssignment){
                VarAssignment va = (VarAssignment)initialization_exp;
                va.run(localSymbolTable);
            }
        }

        //ejecucion del for
        if (condition != null){
            while ((Boolean)condition.run(localSymbolTable)){
               TS for_local_symbol_table = new TS();
               for_local_symbol_table.addAll(localSymbolTable.getVariables(), localSymbolTable.functions);
                //ejecucion de las instrucciones
                for (Instruction in : ins){
                    if (in != null){
                        in.run(for_local_symbol_table);
                    }
                }

                //actualizacion de variable (en caso de que se declare)
                if (actualization_exp != null){
                    if (actualization_exp instanceof Operation){
                        Operation op = (Operation)actualization_exp;
                        op.run(for_local_symbol_table);
                    }else if (actualization_exp instanceof VarAssignment){
                        VarAssignment va = (VarAssignment)actualization_exp;
                        va.run(for_local_symbol_table);
                    }
                }
            }
        }else{
            while (true){
                //ejecucion de las instrucciones
                TS for_local_symbol_table = new TS();
                 for_local_symbol_table.addAll(localSymbolTable.getVariables(), localSymbolTable.functions);
                for (Instruction in : ins){
                    if (in != null){
                       Object obj =  in.run(for_local_symbol_table);
                       if (obj!=null){
                           return obj;
                       }
                    }
                }
                //actualizacion de variable (en caso de que se declare)
                if (actualization_exp != null){
                    if (actualization_exp instanceof Operation){
                        Operation op = (Operation)actualization_exp;
                        op.run(for_local_symbol_table);
                    }else if (actualization_exp instanceof VarAssignment){
                        VarAssignment va = (VarAssignment)actualization_exp;
                        va.run(for_local_symbol_table);
                    }
                }
            }
        }

        return null;
    }
}