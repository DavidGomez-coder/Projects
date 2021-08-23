package ctdGenerator;

import java.util.*;

public class VarDeclaration {

   /**
    * Metodo para la declaracion de una lista de variables
    * @varNames lista de variables a declarar
    */
   public static void var_declaration (String varName, Expression e, String type){
            if (TS.findVarInActualBlock(varName)){
                Global.error("variable '" + varName + "' ya ha sido declarada");
            }
            int blockNumber = TS.blockNumber();
            if (blockNumber == 0){
                TS.addVar(new Var(varName, e.getType(), e.getValue()));
                System.out.println("\t" + varName + " = " + e.getValue() + ";");
            }else{
                TS.addVar(new Var(varName+"_"+blockNumber, e.getType(), e.getValue()));
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
                if (v.getType().equals(Global.FLOAT) && e.getType().equals(Global.INTEGER)){
                    v.setValue(e.getValue());
                    System.out.println("\t" + v.getName() + " = (float) " + e.getValue() + ";");
                }else{
                    System.out.println("#" + v + ", " + e);
                    if (!v.getType().equals(e.getType())){
                        Global.error("error de tipos");
                    }
                    v.setValue(e.getValue());
                    System.out.println("\t" + v.getName() + " = " + e.getValue()+ ";");
                    System.out.println("#ACCESO A LA VARIABLE " + v);
                }
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
    * Metodo que inicializa un array a la lista correspondiente. En caso de que no
    * se defina una lista, se inicializan a 0 sus valores que no se hayan predefinido
    * El booleano @redefined se utiliza para saber si el array con nombre @array_name 
    * es nuevo (@redefined = true) o no (@redefined = false)
    */
   public static String array_inicialization (String array_name, Array ar, String type, boolean redefined){       
        //inicializacion del array
        List l = ar.getList();
        String res = null;
        //chekeamos que son todos del mismo tipo y si hay una variable declarada con el 
        //mismo nombre
        if (!redefined && TS.findVarInActualBlock(array_name)){
            Global.error("variable '" + array_name + "' ya ha sido declarada");
        }

        if (same_array_type(l)){
            //variable temporal para la definicion
            String temp0 = TS.newTempVar();
            //numero de bloque
            int blockNumber = TS.blockNumber();
            if (blockNumber == 0){
                System.out.println("$"+array_name+"_length = "+ ar.size() + ";");
                Var v = new Var(array_name, type, ar);
                //preinicializacion
                if (l.size() > 0){
                    //comprobacion de array del mismo tipo
                    if (!ar.getType().equals(type_of(l.get(0)))){
                        Global.error("error de tipos");
                    }
                    for (int i=0;i<l.size();i++){             
                        Expression o = (Expression)l.get(i);
                        System.out.println("\t" + temp0 + "[" + i + "] = " + o.getValue() + ";");
                    }
                    //asignacion de los valores
                    String temp1 = TS.newTempVar();
                    for (int i=0;i<l.size();i++){
                        System.out.println("\t" + temp1 + " = " + temp0 + "[" + i + "];");
                        System.out.println("\t" + array_name + "[" + i + "] = " + temp1 + ";"); 
                    }
                    System.out.println("\t" + array_name + " = " + temp0 + ";");
                    res = temp0;
                    TS.addVar(v);
                }else{
                    v = ceroArrayValue(array_name, ar);
                    TS.addVar(v);
                }
                
            }else{
                System.out.println("$"+array_name+"_"+blockNumber+"_length = "+ ar.size() + ";");
                Var v = new Var(array_name+"_"+blockNumber, type, ar);
                //preinicializacion
                if (l.size() > 0){
                    //comprobacion de array del mismo tipo  
                    if (!ar.getType().equals(type_of(l.get(0)))){
                        Global.error("error de tipos");
                    }
                    for (int i=0;i<l.size();i++){
                        Expression o = (Expression)l.get(i);
                        System.out.println("\t" + temp0 + "[" + i + "] = " + o.getValue() + ";");
                    }
                    //asignacion de los valores
                    String temp1 = TS.newTempVar();
                    for (int i=0;i<l.size();i++){
                        System.out.println("\t" + temp1 + " = " + temp0 + "[" + i + "];");
                        System.out.println("\t" + array_name+"_"+blockNumber + "[" + i + "] = " + temp1 + ";"); 
                    }
                    System.out.println("\t" + array_name+"_"+blockNumber + " = " + temp0 + ";");
                    res = temp0;
                    TS.addVar(v); 
                }else{
                    v = ceroArrayValue(array_name+"_"+blockNumber, ar);
                    TS.addVar(v);
                }
                               
            }
        }else if (!same_array_type(l)){
            Global.error("array de diferentes tipos");
        }
        return res;
   }

   /**
    * Devuelve una variable de arrays de ceros
    */
   private static Var ceroArrayValue (String varName, Array ar){
        List nList = new ArrayList<>();
        for (int i=0;i<ar.size();i++){
            if (ar.getType().equals(Global.INTEGER)){
                nList.add(new Integer(0));
            }else if (ar.getType().equals(Global.FLOAT)){
                nList.add(new Float(0));
            }
        }
        Array nArray = ar;
        nArray.setList(nList);
        return new Var(varName, Global.ARRAY, nArray);
   }

   /**
    * Metodo para reasignar el valor de una posicion concreta del un array
    */
   public static Expression reasign_array_index_value (String varName, String index, Expression e){
       Var v = TS.getVar(varName);
       if (v==null){
            Global.error("variable '" + varName + "' no declarada");
        }
        if (!(v.getType().equals(Global.ARRAY))){
            Global.error("variable '" + varName + "' no es un array");
        }
        Array ar = (Array)v.getValue();
        range_check(varName, ar, index);
        if (ar.getType().equals(Global.FLOAT)){
            if (e.getType().equals(Global.INTEGER)){
                Expression nexp = Operation.cast(e, Global.FLOAT);
                System.out.println("\t" + varName + "[" +  index +"] = " + nexp.getValue() + ";");
            }else if (e.getType().equals(Global.FLOAT)){
                System.out.println("\t" + varName + "[" +  index +"] = " + e.getValue() + ";");
            }      
        }else if (ar.getType().equals(e.getType())){
            System.out.println("\t" + varName + "[" +  index +"] = " + e.getValue() + ";");
            
        }else{
            Global.error("error de tipos");
        }
        
        return new Expression(e.getType(), varName);
   }

   /**
    * Metodo para la asignacion multiple de una lista a otras variables (que son arrays)
    * Se inicializan solo los @value.size() primeros valores 
    */
   public static void multipleAsigArrayVar (List<String> varNames, List value){
       for (String varName : varNames){
            Var v = TS.getVar(varName);
            if (v==null){
                Global.error("variable '" + varName + "' no declarada");
            }
            if (!v.getType().equals(Global.ARRAY)){
                Global.error("variable '" + varName + "' no es un array");
            }
            
            Array vArray = (Array)v.getValue();

            if (!same_array_type(value)){
                Global.error("error de tipos");
            }
            System.out.println("#" + v.getName() + " tipo " + vArray.getType());
            if (!vArray.getType().equals(type_of(value.get(0)))){
                Global.error("error de tipos");
            }

            //si el array tiene una longitud mayor que el array declarado, se lanza un error
            if (value.size() > vArray.size()){
                Global.error("error en la dimension del array '" + varName + "'");
            }
            
            //numeros de ceros que hay que insertar en el array (valores no inicializados)
            //inicializacion del array
            List nList = value;
            String temp0 = TS.newTempVar();
            String temp1 = TS.newTempVar();
            //reiniciacion solo de los value.size() valores
            for (int i=0;i<value.size(); i++){
                Object o = value.get(i);
                if (o instanceof Expression){
                    Expression nexp = (Expression)o;
                    System.out.println("\t" + temp0 + "[" + i + "] = " + nexp.getValue() + ";");
                    vArray.setValue(i, nexp.getValue());
                }else{
                    System.out.println("\t" + temp0 + "[" + i + "] = " + o.toString() + ";");
                    String type = type_of(o);
                    if (type.equals(Global.FLOAT)){
                        vArray.setValue(i, Float.parseFloat(o.toString()));
                    }else if (type.equals(Global.INTEGER)){
                        vArray.setValue(i, Integer.parseInt(o.toString()));
                    }
                }
                
            }
            for (int i=0;i<value.size(); i++){
                System.out.println("\t" + temp1 + " = " + temp0 + "[" + i + "];");
                System.out.println("\t" + v.getName() + "[" + i + "] = " + temp1 + ";");
            }


            for (int i=0;i<value.size(); i++){
                nList.set(i, value.get(i));               
            }
            vArray.setList(nList);
            v.setValue(vArray);        
       }
   }

   /**
    * Indica si todos los objetos de la lista @l son del mismo tipo
    */
   public static boolean same_array_type (List l){
       boolean same_type = true;
       int i = 0;
       while (i<l.size() && same_type){
           int j = i + 1;
           while (j<l.size() && same_type){
                Object o1 = l.get(i);
                Object o2 = l.get(j);
                if (o1!=null && o2!= null && !type_of(o1).equals(type_of(o2))){
                    same_type =  false;
                }
                j++;
           }
           i++;
       }
       return same_type;
   }

   /**
    * Devuelve en forma de String el tipo del objeto o
    */
   public static String type_of(Object o){
       if (o instanceof Integer){
           return Global.INTEGER;
       }else if (o instanceof Float || o instanceof Double){
           return Global.FLOAT;
       }else if (o instanceof Expression){
            Expression e = (Expression)o;
            return e.getType();
       }
        return "Object";
   }

   /**
    * Metodo para devolver el valor de un indice en un array
    */
   public static Expression get_array_index_value (String varName, String index){
        Var v = TS.getVar(varName);
        if (v == null){
            Global.error("variable " + varName + "' no declarada");
        }

        if (!(v.getValue() instanceof Array)){
            Global.error("variable '" + varName + "' no es un array");
        }
        Array ar = (Array)v.getValue();
        
        //comprobacion de rango
        String tempVar = range_check(varName, ar, index);
        System.out.println("\t" + tempVar + " = " + varName + "[" + index + "]" + ";");
        return new Expression (ar.getType(), tempVar);
   }

   /**
    * Metodo para devolver el valor de un indice en un bucle for (variable del tipo a[i])
    */
   public static Expression get_array_index_value_for_in (String varName, String index){
    Var v = TS.getVar(varName);
    if (v == null){
        Global.error("variable " + varName + "' no declarada");
    }

    if (!(v.getValue() instanceof Array)){
        Global.error("variable '" + varName + "' no es un array");
    }
    Array ar = (Array)v.getValue();
    
    //comprobacion de rango
    String tempVar = range_check(varName, ar, index);
    System.out.println("\t" + tempVar + " = " + varName + "[" + index + "]" + ";");
    Expression e = new Expression(ar.getType(), new Expression (Global.MUTABLE_LOOP_VAR, varName + "[" + index + "]"));
    return e;
   }

   /**
    * Metodo para la comprobacion del rango
    */
   private static String range_check (String varName, Array ar, String index){
        String l0 = TS.newLabel();
        String l1 = TS.newLabel();
        System.out.println("#Comprobacion de rango");
        System.out.println("\tif (" + index + " < 0) goto " + l0 + ";");
        System.out.println("\tif (" + ar.size() + " < " + index + ") goto " + l0 + ";");
        System.out.println("\tif (" + ar.size() + " == " + index + ") goto " + l0 + ";");  
        System.out.println("\tgoto " + l1 + ";");

        Global.label(l0);
        System.out.println("\terror;");
        System.out.println("\thalt;");
        Global.label(l1);
        String tempVar = TS.newTempVar();
         // System.out.println("\t" + tempVar + " = " + varName + "[" + index + "]" + ";");
        return tempVar;
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

    public static Expression getVar (String varName, String type){
        Var v = TS.getVar(varName);
        if (v==null){
            Global.error ("variable '" + varName + "' no declarada");
        }
       // System.out.println("#ACESO A LA VARIABLE: " + v);
        Expression e =  new Expression(v.getType(), v.getName());
        if (type==null){
            return e;
        }else{
           return Operation.cast(e, type);
        }
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