package instructions;

import java.util.*;

public class DoWhileIns implements Instruction {

    private List<Instruction> instructions;
    private Operation condition;

    public DoWhileIns (List<Instruction> instructions, Operation condition){
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object run (TS symbolTable){
        do {
            TS localSymbolTable = new TS();
            localSymbolTable.addAll(symbolTable.getVariables(), symbolTable.functions);
            for (Instruction in : instructions){
                if (in!=null){
                    Object ob = in.run(localSymbolTable);
                    if (ob != null){
                        return ob;
                    }
                }
            }    
        }while ((Boolean)condition.run(symbolTable));

        return null;
    }


}