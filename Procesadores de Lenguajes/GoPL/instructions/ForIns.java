package instructions;

import java.util.*;

public class ForIns implements Instruction {

    private String varName;
    private Operation init_var_val;
    private Operation condition;
    private String increment; 
    private List<Instruction> list_ins;

    public ForIns (String varName, Operation init_var_val, Operation condition, String increment, List<Instruction> list_ins){
        this.varName = varName;
        this.init_var_val = init_var_val;
        this.condition = condition;
        this.increment = increment;
        this.list_ins = list_ins;
    }

    @Override
    public Object run (TS symbolTable){
        if (varName != null){
            VarDeclaration vd = new VarDeclaration(varName, Global.VARIA, init_var_val);
            vd.run(symbolTable);
            while ((Boolean)condition.run(symbolTable)){
                for (Instruction in : list_ins){
                    if (in!=null){
                        in.run(symbolTable);
                    }
                }
                Operation operator = new Operation(increment, Global.VARIABLE);
                VarAssignment va = new VarAssignment(increment, 
                                new Operation(operator, Global.PLUS, new Operation(new Integer(1))));
                va.run(symbolTable);
            }
        }else{
            while ((Boolean)condition.run(symbolTable)){
                for (Instruction in : list_ins){
                    if (in!=null){
                        in.run(symbolTable);
                    }
                }
            }
        }
        
        return null;
    }
}