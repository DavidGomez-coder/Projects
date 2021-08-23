package ctdGenerator;

public class Operation {


    /**
     * Expresiones aritmeticas
     * @e1 operando 1
     * @operator operador
     * @e2 operando 2
     */
    public static Expression arithmetic (Expression e1, int operator, Expression e2, String castType){
        String v = TS.newTempVar();
        String type = null;
        String res = null;
        if (e1.getType().equals(Global.INTEGER) && e2.getType().equals(Global.INTEGER)){
            type = Global.INTEGER;
            res = integerOperation (v, e1, operator, e2, castType);
        }else if (e1.getType().equals(Global.FLOAT) || e2.getType().equals(Global.FLOAT)){
            type = Global.FLOAT;
            Expression ex1 = e1;
            Expression ex2 = e2;
            //hacemos las conversiones necesarias
            if (!e1.getType().equals(Global.FLOAT)){
                ex1 = cast(ex1, Global.FLOAT);
            }
            if (!e2.getType().equals(Global.FLOAT)){
                ex2 = cast(ex2, Global.FLOAT);
            }
            res =  floatOperation (v, ex1, operator, ex2);
        }

        return new Expression (type, res);
    }


    /**
     * Operaciones sobre numeros enteros
     * @v variable temporal definda sobre la expresion
     * @e1 operando 1
     * @operator operador
     * @e2 operando 2
     */
    private static String integerOperation (String v, Expression e1, int operator,  Expression e2, String castType){
        String res = v;
        if (e1 != null){
            Object v1 = e1.getValue();
            Object v2 = e2.getValue();
            
            if (castType!=null && castType.equals(Global.FLOAT)){
                String t1 = TS.newTempVar();
                String t2 = TS.newTempVar();
                System.out.println("\t" + t1 + " = (float) " + v1 + ";");
                System.out.println("\t" + t2 + " = (float) " + v2 + ";");
                Expression ex1 = new Expression(Global.FLOAT, t1);
                Expression ex2 = new Expression(Global.FLOAT, t2);
                res = floatOperation(v, ex1, operator, ex2);
            
            }else {
                if (operator == Global.PLUS){
                    System.out.println("\t" + v + " = " + v1.toString() + " + " + v2.toString() + ";");
                }else if (operator == Global.MINUS){
                    System.out.println("\t" + v + " = " + v1.toString() + " - " + v2.toString() + ";");
                }else if (operator == Global.MUL){
                    System.out.println("\t" + v + " = " + v1.toString() + " * " + v2.toString() + ";");
                }else if (operator == Global.DIV){
                    System.out.println("\t" + v + " = " + v1.toString() + " / " + v2.toString() + ";");
                }else if (operator == Global.MOD){
                    String temp0 = TS.newTempVar();
                    System.out.println("\t" + temp0 + " = " + v1 + " / " + v2 + ";");
                    String temp1 = TS.newTempVar();
                    System.out.println("\t" + temp1 + " = " + temp0 + " * " + v2 + ";");
                    System.out.println("\t" + v + " = " + v1 + " - " + temp1 + ";");
                
            }}
        }
          else{
                Object v2 = e2.getValue();
                if (operator == Global.MINUS){
                    String nV = TS.newTempVar();
                    System.out.println("\t" + v + " =  -" + v2.toString() + ";");
                    System.out.println("\t" + nV + " = " + v + ";");
                    res = nV;
                }   
             }
            
        return res;
    }


     /**
     * Operaciones sobre numeros flotantes (double)
     * @v variable temporal definda sobre la expresion
     * @e1 operando 1
     * @operator operador
     * @e2 operando 2
     */
    public static String floatOperation (String v, Expression e1, int operator, Expression e2){
        String res = v;
        if (e1 != null){
            Object v1 = e1.getValue();
            Object v2 = e2.getValue();
            if (operator == Global.PLUS){
                System.out.println("\t" + v + " = " + v1.toString() + " +r " + v2.toString() + ";");
            }else if (operator == Global.MINUS){
                System.out.println("\t" + v + " = " + v1.toString() + " -r " + v2.toString() + ";");
            }else if (operator == Global.MUL){
                System.out.println("\t" + v + " = " + v1.toString() + " *r " + v2.toString() + ";");
            }else if (operator == Global.DIV){
                System.out.println("\t" + v + " = " + v1.toString() + " /r " + v2.toString() + ";");
            }else if (operator == Global.MOD){
                String temp0 = TS.newTempVar();
                System.out.println("\t" + temp0 + " = " + v1 + " /r " + v2 + ";");
                String temp1 = TS.newTempVar();
                System.out.println("\t" + temp1 + " = " + temp0 + " *r " + v2 + ";");
                System.out.println("\t" + v + " = " + v1 + " - " + temp1 + ";");
            }
        }else {
            Object v2 = e2.getValue();
            if (operator == Global.MINUS){
                String nV = TS.newTempVar();
                System.out.println("\t" + v + " =  -" + v2.toString() + ";");
                System.out.println("\t" + nV + " = " + v + ";");
                res = nV;
            }   
        }
        return res;
    }

    /**
     * Metodo para hacer cast a una expression
     * @e expresion
     * @type tipo a castear
     */
    public static Expression cast (Expression e, String type){
        if (type!=null){
            String tempVar = TS.newTempVar();
            String t = "";
            if (type.equals(Global.INTEGER)){
                System.out.println("#CASTING A INTEGER: " + e.getValue());
                t = "(int)";
            }else if (type.equals(Global.FLOAT)){
                System.out.println("#CASTING A FLOAT: " + e.getValue());
                t = "(float)";
            }else{
                return e;
            }
            System.out.println("\t" + tempVar + " = " + t + " " + e.getValue() + ";");
            return new Expression(type, tempVar);
        }
        return e;
    }

    /**
     * Devuelve una condicion. Simula al tipo booleano y se utiliza para las sentencias de control
     */
    public static Condition condition (Expression e1, int operator, Expression e2){
        //creamos nuevas etiquetas
        String trueLabel = TS.newLabel();
        String falseLabel = TS.newLabel();
        Condition c = new Condition(trueLabel, falseLabel);

        String v1 = e1.getValue().toString();
        String v2 = e2.getValue().toString();

        if (operator == Global.EQ){
            System.out.println("\tif (" + v1 + " == " + v2 + ") goto " + c.true_label() + ";");
            System.out.println("\tgoto " + c.false_label() + ";");
        }else if (operator == Global.NE){
            System.out.println("\tif (" + v1 + " == " + v2 + ") goto " + c.false_label() + ";");
            System.out.println("\tgoto " + c.true_label() + ";");
        }else if (operator == Global.LT){
            System.out.println("\tif (" + v1 + " < " + v2 + ") goto " + c.true_label() + ";");
            System.out.println("\tgoto " + c.false_label() + ";");
        }else if (operator == Global.LE){
            System.out.println("\tif (" + v2 + " < " + v1 + ") goto " + c.false_label() + ";");
            System.out.println("\tgoto " + c.true_label() + ";");
        }else if (operator == Global.GT){
            System.out.println("\tif (" + v2 + " < " + v1 + ") goto " + c.true_label() + ";");
            System.out.println("\tgoto " + c.false_label() + ";");
        }else if (operator == Global.GE){
            System.out.println("\tif (" + v1 + " < " + v2 + ") goto " + c.false_label() + ";");
            System.out.println("\tgoto " + c.true_label() + ";");
        }

        return c;
    }

    public static Condition booleanOperator (Condition o1, int operator, Condition o2){
        Condition c = o2;
            if (operator == Global.NOT){
                c = new Condition(o2.false_label(), o2.true_label());
            }else if (operator == Global.AND){
                System.out.println(o1.false_label() + ":");
                System.out.println("\tgoto " + o2.true_label() + ";");
            }else if (operator == Global.OR){
                System.out.println(o1.true_label() + ":");
                System.out.println("\tgoto " + o2.false_label() + ";");
            }
        return c;
    }

}