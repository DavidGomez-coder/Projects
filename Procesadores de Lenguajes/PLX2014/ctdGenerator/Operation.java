package ctdGenerator;

public class Operation {


    /**
     * Expresiones aritmeticas
     * @e1 operando 1
     * @operator operador
     * @e2 operando 2
     */
    public static Expression arithmetic (Expression e1, int operator, Expression e2){
        String v = TS.newTempVar();
        String type = null;
        if (e1.getType().equals(Global.INTEGER) && e2.getType().equals(Global.INTEGER)){
            type = Global.INTEGER;
            integerOperation (v, e1, operator, e2);
        }

        return new Expression (type, v);
    }


    /**
     * Operaciones sobre numeros enteros
     * @v variable temporal definda sobre la expresion
     * @e1 operando 1
     * @operator operador
     * @e2 operando 2
     */
    private static void integerOperation (String v, Expression e1, int operator,  Expression e2){
        if (e1 != null){
            Object v1 = e1.getValue();
            Object v2 = e2.getValue();
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
            }
        }else {
            Object v2 = e2.getValue();
            if (operator == Global.MINUS){
                String nV = TS.newTempVar();
                System.out.println("\t" + v + " =  -" + v2.toString() + ";");
                System.out.println("\t" + nV + " = " + v);
            }
        }

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