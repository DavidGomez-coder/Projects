package ctdGenerator;

public class Condition {

    private String trueLabel;
    private String falseLabel;

    public Condition (String trueLabel, String falseLabel){
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public String true_label (){
        return this.trueLabel;
    }

    public String false_label (){
        return this.falseLabel;
    }

    public void setTrueLabel (String trueLabel){
        this.trueLabel = trueLabel;
    }

    public void setFalseLabel (String falseLabel){
        this.falseLabel = falseLabel;
    }
}