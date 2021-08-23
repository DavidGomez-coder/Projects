package ctdGenerator;

import java.util.*;

public class ForInLoop {

    private String temp0;
    private String headLabel;
    private String bodyLabel;
    private String exitLabel;
    private List list;
    private Var var;
    private Condition c;
    private Expression loopVar;

    public ForInLoop (String headLabel, Var var, Expression loopVar){
        if (!loopVar.getType().equals(var.getType())){
            Global.error("error de tipos");
        }
        //se utiliza para el valor que toma la variable (puede ser una variable predefinida
        //o un valor dentro del array)
        if (loopVar.getValue() instanceof Expression){
            this.loopVar = (Expression)loopVar.getValue();
        }else {
            this.loopVar = loopVar;
        }
     
        this.temp0 = TS.newTempVar();
        this.headLabel = headLabel;
        this.var = var;
        this.list = (List)var.getValue();
        init_for_in_loop();
        //expresiones de la condicion
        Expression e1 = new Expression(Global.TEMP, temp0);
        Expression e2 = new Expression(Global.INTEGER, this.list.size());
        this.c = Operation.condition(e1, Global.LT, e2);
        this.bodyLabel = c.true_label();
        this.exitLabel = c.false_label();
    }

    public void init_for_in_loop(){
        System.out.println("\t" + temp0 + " = -1;");
        Global.label(this.headLabel);
        System.out.println("\t" + temp0 + " = " + temp0 + " + 1; ");
    }

    public void init_body(){
        Global.label(this.bodyLabel);
        
        if (loopVar.getType().equals(Global.MUTABLE_LOOP_VAR)){
            String tempVar = TS.newTempVar();

            System.out.println("\t" + tempVar + " = " + var.getName() + "[" + temp0 + "];");
            System.out.println("\t" + loopVar.getValue() + " = " + tempVar + ";");

        }else{
            System.out.println("\t" + loopVar.getValue() + " = " + var.getName() + "[" + temp0 + "];");
        }
    }

    public void end_body(){
        Global.gotoLabel(this.headLabel); 
    }

    public void exit_label(){
        Global.label(this.exitLabel);
    }


    public static void init_array_declaration (String temp, List list){
        for (int i=0;i<list.size();i++){
            Expression e = (Expression)list.get(i);
            System.out.println("\t" + temp + "[" + i + "] = " + e.getValue().toString() + ";");
        }
    }

}