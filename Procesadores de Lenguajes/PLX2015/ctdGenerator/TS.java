package ctdGenerator;

import java.util.*;

public class TS {

    private static int numVar = -1;
    private static int numLab = -1;

    private static List<List<Var>> variables = new ArrayList<>();
    private static int blockNumber;

    static {
        variables.add(new ArrayList<>());
        blockNumber = 0;
    }


    /**
     * Crea una nueva variable temporal
     */
    public static String newTempVar(){
        numVar++;
        return "$t" + numVar;
    }
    
    /**
     * Crea una nueva etiqueta
     */
    public static String newLabel (){
        numLab++;
        return "$L" + numLab;
    }

    /**
     * Crea un nuevo bloque
     */
    public static void newBlock (){
        blockNumber++;
        variables.add(blockNumber, new ArrayList<>());
    }

    /**
     * Elimina el ultimo bloque declarado
     */
    public static void deleteBlock(){
        variables.remove(blockNumber);
        blockNumber--;
    }

    /**
     * Devuelve el nombre que tiene una variable definida dentro de 
     * un bloque
     */
    public static String varBlockDef (String varName){
        return varName + "_" + blockNumber;
    }

    /**
     * Devuelve el numero de bloque actual
     */
    public static int blockNumber(){
        return blockNumber;
    }

    /**
     * Anyade una nueva variable
     */
    public static void addVar (Var v){
        variables.get(blockNumber).add(v);
    }

    /**
     * Devuelve true si la variable esta definida en y false en otro caso
     */
    public static boolean varIsDefined (String varName){
        boolean find = false;
 
        int k = variables.size()-1;
        while (k>=0 && !find){
            int i = 0;
            List<Var> list = variables.get(k);
            while (i<list.size() && !find){
                Var v = list.get(i);
                if (v.getName().equals(varName)){
                    find = true;
                }
                i++;
            }
            k--;
        }
        return find;
    }

    /**
     * Devuelve la variable asociada al nombre @varName
     */
    public static Var getVar (String name) {
        String varName = name;
        Var v = null;
        int i = blockNumber;
        while (i >= 0){
            List<Var> vars_block = variables.get(i);
            int j = 0;
            if (i==0){
                varName = name;
            }else{
                varName = name+"_"+i;
            }
            //System.out.println("#BUSCANDO VARIABLE: " + varName);
            while (j< vars_block.size()){
                Var var = vars_block.get(j);
                if (var.getName().equals(varName)){
                    v = var;
                    return var;
                }j++;
            }
            i--;
        }
        return v;
    }

    /*  Indica si una variable esta definida en el bloque actual */
    public static boolean findVarInActualBlock (String varName){
        boolean ok = false;
        int i = 0;
        List<Var> list = variables.get(blockNumber);
        while (i<list.size() && !ok){
            Var v = list.get(i);
            if (v.getName().equals(varName)){
                ok = true;
            }
            i++;
        }
        return ok;
    }

    /**
    * Busca si una variable con nombre @varName ha sido o no declarada
    */
    public static boolean search(String varName){
		int i = 0;
        boolean contains = false;
		while((blockNumber  -1>= i) && !contains){
            List<Var> vars = variables.get(blockNumber-i);
            //System.out.println("#BUSCANDO EN " + vars.toString());
            int j = 0;
            while (j<vars.size() && !contains){
                Var v = vars.get(j);
                if (v.getName().equals(varName)){
                    contains = true;
                }
                j++;
            }
			i++;
		}
		if(blockNumber < i){
			return false;
		} else {
			return true;
		}
	}

    /**
     * Devuelve el numero del bloque en el que se ha definido la variable @varName
     */
    public static int getBlockNumber (String varName){
        int  i = 0;
		int act = blockNumber;
        boolean contains = false;
		while (blockNumber >i && !contains){
            List<Var> var_block = variables.get(blockNumber-i);
            
            int j = 0;
            while (j<var_block.size() && !contains){
                String name = varName+"_"+act;
                Var v = var_block.get(j);
                if (v.getName().equals(name)){
                    contains = true;
                }j++;
            }
			i++;
			act--;
		}
		if (blockNumber == i){
			return 0;
		}else{
			return act;
		}
    }

    /**
     * Muestra la tabla de simbolos
     */
    public static void show (){
        System.out.println("\n-----------------------------------------");
        for (int i = 0;i<variables.size();i++){
            System.out.println("BLOQUE " + i + ":");
            List<Var> vars = variables.get(i);
            for (Var v : vars){
                System.out.println("\t" + v);
            }
        }
    }
}