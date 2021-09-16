package instructions;

public class Return implements Instruction {

    private Operation exp;

    public Return (Operation exp){
        this.exp = exp;
    }

    @Override
    public Object run (TS symbolTable){
        return exp.run(symbolTable);
    }
}