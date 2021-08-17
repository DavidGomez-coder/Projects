package ctdGenerator;

public class ForLoop {

   public static void loop_var(Expression v, int operator, Expression e2){
       if (operator == Global.PLUS){
            System.out.println("\t" + v.getValue() + " = " +  v.getValue() + " + " + e2.getValue() + ";");
       }else if (operator == Global.MINUS){
            System.out.println("\t" +  v.getValue() + " = " +  v.getValue() + " - " + e2.getValue() + ";");
       }
   }

}