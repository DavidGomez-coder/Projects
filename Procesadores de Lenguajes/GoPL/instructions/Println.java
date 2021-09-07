package instructions;

import java.util.*;

/**
 *	Esta clse implementa la instrucción Println. Se encarga de mostrar una instrucción 
 * (una operación, un string, el valor de una variable, un array, ...) El resultado se mostrará
 * dependiendo de la implementación del método @toString de cada una de las instrucciones que se 
 * invoquen
*/
public class Println implements Instruction {

    private Instruction ins;

    public Println (Instruction ins){
        this.ins = ins;
    }

    private List list_varias;
	public Println (List list_varias){
		this.list_varias = list_varias;	
	}


    @Override
    public Object run (TS symbolTable){
        if (this.list_varias!=null){
			 	System.out.println();	
				for (Object o : list_varias){
					Operation operation = (Operation)o;
					Object l = operation.run(symbolTable);
					if (l instanceof List){
						for (Object os : (List)l){
							if (os instanceof Operation){
								Operation op = (Operation)os;							
								System.out.print(op.run(symbolTable).toString() + " ");
							}else{
								Var v = symbolTable.getVar(os.toString());
								if (v==null){
									Err err = new Err("ERROR (variable no declarada)");
									return err;
								}
								System.out.print(v.getValue() + " ");
							}
						}
					}else{
						System.out.print(l.toString() + " ");
					}
					
				}
                System.out.println();
			}else if (ins!=null){
				System.out.println(ins.run(symbolTable).toString());
			}else{
				Err err = new Err("ERROR");
				err.run(symbolTable);
			}
        return null;
    }
}