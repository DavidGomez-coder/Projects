package instructions;

/**
 * Interfaz utilizada para crear las instrucciones que componen
 * un programa. El metodo run se le pasa una tabla de simbolos que
 * puede ser global o local (En caso de que sea local, se creara una nueva tabla de simbolos
 * y a la que se le unira la tabla de simbolos global y se le pasara como parametro la nueva creada)
*/
public interface Instruction {

	public Object run (TS symbolTable);
}