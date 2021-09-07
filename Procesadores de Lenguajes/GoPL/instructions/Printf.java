package instructions;

import java.util.*;

public class Printf implements Instruction{

    public static final String VALUE_VERB = "%v";
    public static final String TYPE_VERB  = "%T";

    private String str;
    private List list_varia;
    private List operations;

    public Printf (String str, List list_varia){
        this.str = str;
        this.list_varia = list_varia;
        this.operations = new ArrayList<>();
    }


    @Override
    public Object run (TS symbolTable){

        if (list_varia.size()==0){
            Err err = new Err("ERROR");
            err.run(symbolTable);
        }

        for (Object o : list_varia){
            Operation operation = (Operation)o;
            Object l = operation.run(symbolTable);
            if (l instanceof List){
                for (Object os : (List)l){
                    if (os instanceof Operation){
                        Operation op = (Operation)os;							
                        operations.add(op); //añadimos la operacion
                    }else{
                        Var v = symbolTable.getVar(os.toString());
                        if (v==null){
                            Err err = new Err("ERROR (variable no declarada)");
                            return err;
                        }
                        operations.add(v); //añadimos la variable
                    }
                }
            }else{
                operations.add(operation);
            }
            
        }
        //recorremos el array de operaciones a imprimir
        String myString = replaceVerbs(symbolTable);
        //miramos si quedan verbos por reemplazar (la lista de datos es menor que el numero de verbos)
        if (myString.contains("%v") || myString.contains("%T")){
            Err err = new Err("ERROR");
            err.run(symbolTable);
        }
        

        //imprimimos
        System.out.print(myString);
        return null;
    }

    /**
     * Metodo para reemplazar los verbos en un string
     */
    private String replaceVerbs (TS symbolTable){
        int count = 0;
        String myString = str;
        while (count < operations.size()){
            String first_verb_to_replace = getFirstVerbIndex(myString);
            String newString = getVerbString(operations.get(count), first_verb_to_replace, symbolTable, myString);
            myString = newString;
            
            count++;
        }

        
        return myString;
    }

    private String getVerbString (Object o, String verb, TS symbolTable, String str){
        String res = str;
        if (verb.equals(TYPE_VERB)){
            if (o instanceof Operation){
                Operation op = (Operation)o;
                res = res.replace("%T",Global.typeOf(op.run(symbolTable)));
            }else if (o instanceof Var){
                Var v = (Var)o;
                res = res.replace("%T", v.getType());
            }
        }else if (verb.equals(VALUE_VERB)){
            if (o instanceof Operation){
                Operation op = (Operation)o;
                res = res.replace("%v", op.run(symbolTable).toString());
            }else if (o instanceof Var){
                Var v = (Var)o;
                res = res.replace("%v", v.getValue().toString());

            }
        }
        return res;
    }


    private String getFirstVerbIndex (String myString){
        int index = myString.indexOf("%");
        if (myString.charAt(index+1) == 'v'){
            return "%v";
        }else if (myString.charAt(index+1) == 'T'){
            return "%T";
        }
        return null;
    }

}