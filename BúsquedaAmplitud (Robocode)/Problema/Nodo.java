package Problema;

public class Nodo {
	private int fila;
	private int columna;
	private Nodo padre;

	private String movimiento; // "ARRIBA","ABAJO","DERECHA","IZQUIERDA",
								// "D_ARRIBA_DERECHA","D_ARRIBA_IZQ", "D_ABAJO_DERECHA", "D_ABAJO_IZQ"
								// "INICIAL"

	// Constructor para el primer nodo
	public Nodo(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.padre = null;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}


	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public Nodo getPadre() {
		return padre;
	}

	public String getGiro() {
		return movimiento;
	}

	// dos nodos son iguales si lo son sus coordenadas
	public boolean equals(Nodo n) {
		return n.fila == this.fila && n.columna == this.columna;
	}

	///////////////////////////////////////
	public void obtenerMovimiento() {
		if (this.getPadre() != null) {
			if (this.getPadre().getFila()-1==this.getFila() && this.getPadre().getColumna()-1==this.getColumna()) {
				this.movimiento = "D_IZQ_ABAJO";
			}else if (this.getPadre().getFila()-1==this.getFila() && this.getPadre().getColumna()+1==this.getColumna()) {
				this.movimiento = "D_IZQ_ARRIBA";
			}else if (this.getPadre().getFila()+1==this.getFila() && this.getPadre().getColumna()+1==this.getColumna()) {
				this.movimiento = "D_DER_ARRIBA";
			}else if (this.getPadre().getFila()+1==this.getFila() && this.getPadre().getColumna()-1==this.getColumna()) {
				this.movimiento = "D_DER_ABAJO";
			}else if (this.getPadre().getFila()-1==this.getFila() && this.getPadre().getColumna()==this.getColumna()) {
				this.movimiento = "IZQUIERDA";
			}else if (this.getPadre().getFila()==this.getFila() && this.getPadre().getColumna()+1==this.getColumna()) {
				this.movimiento = "ARRIBA";
			}else if (this.getPadre().getFila()+1==this.getFila() && this.getPadre().getColumna()==this.getColumna()) {
				this.movimiento = "DERECHA";
			}else {
				this.movimiento = "ABAJO";
			}
		}
	}

	public String toString() {
		return "[" + this.getFila() + "," + this.getColumna() + "]";
	}
}
