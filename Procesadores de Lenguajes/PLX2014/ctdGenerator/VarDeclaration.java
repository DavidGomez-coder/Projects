package ctdGenerator;

import java.util.*;

public class VarDeclaration {

   /**
    * Metodo para la declaracion de una lista de variables
    * @varNames lista de variables a declarar
    */
   public static void int_var_declaration (String varName, Expression e){
            if (TS.findVarInActualBlock(varName)){
                Global.error("variable '" + varName + "' ya ha sido declarada");
            }
            int blockNumber = TS.blockNumber();
            if (blockNumber == 0){
                TS.addVar(new Var(varName, Global.INTEGER, e.getValue()));
                System.out.println("\t" + varName + " = " + e.getValue() + ";");
            }else{
                TS.addVar(new Var(varName+"_"+blockNumber, Global.INTEGER, e.getValue()));
                System.out.println("\t" + varName+"_"+blockNumber + " = " + e.getValue() + ";");
            }

   }

   /**
    * Metodo para la inicializacion multiple de variables asociada a un valor
    * @varNames lista de variables
    * @e valor
    */
   public static void multipleVarInicialization (List<String> varNames, Expression e){
        for (String varName : varNames){
            if (TS.findVarInActualBlock(varName)){
                Global.error("variable '" + varName + "' ya ha sido declarada");
            }
            int blockNumber = TS.blockNumber();
            if (blockNumber == 0){
                TS.addVar(new Var(varName, e.getType(), e.getValue()));
                System.out.println("\t" + varName + " = " + e.getValue()+ ";");
            }else{
                TS.addVar(new Var(varName+"_"+blockNumber, e.getType(), e.getValue()));
                System.out.println("\t" + varName+"_"+blockNumber + " = " + e.getValue()+ ";");
            }
        }
   }

   /**
    * Metodo que actualiza el valor de la variable @varName
    */
   public static void varValAct (String varName, Expression e){
        Var v = TS.getVar(varName);
        if (v==null){
            Global.error("variable '" + varName + "' no declarada");
        }
        v.setValue(e.getValue());
        System.out.println("\t" + varName + " = " + e.getValue() + ";");
        System.out.println("#ACCESO A LA VARIABLE " + v);
    }

   /**
    * Actualizacion de variables ya declaradas
    */
   public static void multipleAsigValueVar (List<String> varNames, Expression e){
        for (String varName : varNames){
            Var v = TS.getVar(varName);
            if (v==null){
                Global.error("variable '" + varName + "' no declarada");
            }
            v.setValue(e.getValue());
            System.out.println("\t" + v.getName() + " = " + e.getValue()+ ";");
            System.out.println("#ACCESO A LA VARIABLE " + v);
        }
   }

   /**
    * Inicializa una variable al valor @e en su declaracion
    */
   public static String intVarInicialization (String varName, Expression e){
       String res;
        int blockNumber = TS.blockNumber();
        if (blockNumber == 0){
            TS.addVar(new Var(varName, e.getType(), e.getValue()));
            System.out.println("\t" + varName + " = " + e.getValue()+ ";");
            res = varName;
        }else{
            TS.addVar(new Var(varName+"_"+blockNumber, e.getType(), e.getValue()));
            System.out.println("\t" + varName+"_"+blockNumber + " = " + e.getValue()+ ";");
            res = varName+"_"+blockNumber;
        }
        return res;
   }

   /**
    * Metodo para devolver una expresion con el valor de una variable definida
    * dentro o fuera del bloque actual de la variable con nombre @varName
    */
   public static Expression getVarValue (String varName){
        Var v = TS.getVar(varName);
        if (v == null){
            Global.error("variable " + varName + "' no declarada");
        }
        return new Expression (v.getType(), v.getValue());
    }

    public static Expression getVar (String varName){
        Var v = TS.getVar(varName);
        if (v==null){
            Global.error ("variable '" + varName + "' no declarada");
        }
       // System.out.println("#ACESO A LA VARIABLE: " + v);
        return new Expression(v.getType(), v.getName());
    }

    /**
     * Incrementa el valor de una variable
     */
    public static Expression varValueInc (String varName, String type){
        Var actValue = TS.getVar(varName);
        if (actValue == null){
            Global.error("variable no declarada");
        }
        Integer nVal = Integer.parseInt(actValue.getValue().toString()) + 1;
        if (actValue.getType().equals(Global.INTEGER)){

            if (type.equals(Global.LEFT)){
                actValue.setValue(nVal);
                System.out.println("\t" + actValue.getName() + " = " + actValue.getName() + " + 1;");
                return new Expression(actValue.getType(), actValue.getName());
            }else if (type.equals(Global.RIGHT)){
                String nVar = TS.newTempVar();
                System.out.println("\t" + nVar + " = " + actValue.getName() + ";");
                System.out.println("\t" + actValue.getName() + " = " + actValue.getName() + " + 1;");
                actValue.setValue(nVal);
                return new Expression(actValue.getType(), nVar);
            }
        }
        return null;
    }

    /**
     * Decrementa el valor de una variable
     */
    public static Expression varValueDec (String varName, String type){
        Var actValue = TS.getVar(varName);
        if (actValue == null){
            Global.error("variable no declarada");
        }
        Integer nVal = Integer.parseInt(actValue.getValue().toString()) - 1;
        if (actValue.getType().equals(Global.INTEGER)){

            if (type.equals(Global.LEFT)){
                actValue.setValue(nVal);
                System.out.println("\t" + actValue.getName() + " = " + actValue.getName() + " - 1;");
                return new Expression(actValue.getType(), actValue.getName());
            }else if (type.equals(Global.RIGHT)){
                String nVar = TS.newTempVar();
                System.out.println("\t" + nVar + " = " + actValue.getName() + ";");
                System.out.println("\t" + actValue.getName() + " = " + actValue.getName() + " - 1;");
                actValue.setValue(nVal);
                return new Expression(actValue.getType(), nVar);
            }
        }
        return null;
    }

}