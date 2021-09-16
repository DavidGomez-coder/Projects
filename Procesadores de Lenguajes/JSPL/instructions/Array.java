package instructions;

import java.util.*;

public class Array implements Instruction {

	private List list;
	private String arrayType;

	public Array (List list){
		this.list = list;
	}

	private Operation l_inf;
	private Operation l_sup;
	public Array (Operation l_inf, Operation l_sup){
		this.l_inf = l_inf;
		this.l_sup = l_sup;
	}

	public List getList(){
		return this.list;
	}

	public void setArrayType (String arrayType){
		this.arrayType = arrayType;
	}



	@Override
	public Object run (TS symbolTable){
		List l = new ArrayList<>();
		if (list == null){//pasamos un rango
			Object inf = l_inf.run(symbolTable);
			Object sup = l_sup.run(symbolTable);
			if (Global.typeOf(inf).equals(Global.typeOf(sup))){
				if (Global.typeOf(inf).equals(Global.INTEGER)){
					int i1 = Integer.parseInt(inf.toString());
					int i2 = Integer.parseInt(sup.toString());
					if (i1 > i2){
						Err err = new Err("error (el limite superior del rango debe ser mayor que el inferior)");
						err.run(symbolTable);
						return err;
					}

					for (int i = i1; i<=i2; i++){
						l.add(i);
					}
				}else if (Global.typeOf(inf).equals(Global.DOUBLE)){
					double d1 = Double.parseDouble(inf.toString());
					double d2 = Double.parseDouble(sup.toString());
					if (d1 > d2){
						Err err = new Err("error (el limite superior del rango debe ser mayor que el inferior)");
						err.run(symbolTable);
						return err;
					}

					for (double d = d1; d<=d2; d++){
						l.add(d);
					}
				}else{
					Err err = new Err("error (tipos no permitidos en la definicion de rango)");
					err.run(symbolTable);
					return err;
				}
			}else{
				Err err = new Err ("error (rango con limites de diferentes tipos)");
				err.run(symbolTable);
				return err;
			}
		}else{
			for (Object o : list){
				Operation op = (Operation)o;
				l.add(op.run(symbolTable));
			}
		}

		this.list = l;
		return l;
	}
}