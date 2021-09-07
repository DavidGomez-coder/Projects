package instructions;

/**
 * Interfaz utilizada para crear las instrucciones que componen
 * un programa. El método run se le pasa una tabla de símbolos que
 * puede ser global o local (En caso de que sea local, se creará una nueva tabla de símbolos
 * y a la que se le unirá la tabla de símbolos global y se le pasará como parámetro)
*/
public interface Instruction {

	public Object run (TS symbolTable);
}