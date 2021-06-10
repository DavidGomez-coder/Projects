package Busqueda;

import Problema.Nodo;
import Problema.Problema;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch {
	private Problema problema;
	private List<Nodo> solucion;
	private List<Nodo> cerrados; // nodos explorados
	private List<Nodo> abiertos; // nodos que se quedan abiertos (sin explorar)

	private int pathCost;
	private int numNodosAbiertos; // se cuentan los visitados y los no visitados
	private int numNodosExplorados;

	public BreadthFirstSearch(Problema problema) throws BreadthFirstsSearchException {
		this.problema = problema;
		cerrados = new ArrayList<>();
		abiertos = new ArrayList<>();

		Nodo inicial = new Nodo(problema.getFilaInicial(), problema.getColumnaInicial());

		busquedaAmplitud(problema, inicial);
		solucion = obtenerSolucion(problema, inicial);
		pathCost = calcularCoste();
		numNodosAbiertos = this.abiertos.size();
		numNodosExplorados = cerrados.size();
	}

	// GETTERS AND SETTERS
	public List<Nodo> getSolucion() {
		return solucion;
	}

	public int getPathCost() {
		return pathCost;
	}

	public int getNumNodosAbiertos() {
		return numNodosAbiertos;
	}
	
	public List<Nodo> getAbiertos(){
		return this.abiertos;
	}
	
	public List<Nodo> getCerrados(){
		return this.cerrados;
	}

	public int getNumNodosExplorados() {
		return numNodosExplorados;
	}

	//

	private int calcularCoste() {
		int cost = 0;
		for (int i = 1; i < solucion.size(); i++) {
			String mov = solucion.get(i).getGiro();
			if (mov.equalsIgnoreCase("ARRIBA") || mov.equalsIgnoreCase("ABAJO") || mov.equalsIgnoreCase("IQUIERDA")
					|| mov.equalsIgnoreCase("DERECHA")) {
				cost += this.problema.getCosteHorizontal();
			} else {
				cost += this.problema.getCosteDiagonal();
			}
		}
		return cost;
	}

	private List<Nodo> obtenerSolucion(Problema p, Nodo inicial) throws BreadthFirstsSearchException {
		List<Nodo> res = new ArrayList<>();
		Nodo nFinal = obtenerNodoFinal(p);
		Nodo act = nFinal;
		while (act != null) {
			res.add(act);
			act = act.getPadre();
		}

		// invertimos el array
		List<Nodo> inv = new ArrayList<>();
		for (int i = res.size() - 1; i >= 0; i--) {
			res.get(i).obtenerMovimiento();
			inv.add(res.get(i));

		}

		if (inv.isEmpty() || !(inv.get(0).equals(inicial) && inv.get(inv.size() - 1).equals(nFinal)))
			throw new BreadthFirstsSearchException("No existe una solucion para el problema generado");

		return inv;
	}

	private void busquedaAmplitud(Problema problema, Nodo inicial) {
		abiertos.add(inicial);
		//cerrados se encuentra vacío
		boolean solucionEncontrada = false;
		while (!abiertos.isEmpty() && !solucionEncontrada) {
			Nodo n = abiertos.get(0);
			abiertos.remove(0);
			cerrados.add(n);
			if (n.getFila() == problema.getFilaFinal() && n.getColumna()==problema.getColumnaFinal()) {
				solucionEncontrada = true;
			}else {
				List<Nodo> sucesores = problema.sucesores(n, cerrados);
				for (Nodo n2 : sucesores) {
					if (!esta(n2,cerrados) && !esta(n2,abiertos)) {
						n = n2;
						abiertos.add(n2);
					}
				}
			}
		}
	}
	
	private boolean esta(Nodo v, List<Nodo> list) {
		boolean ok = false;
		int i = 0;
		while (i < list.size() && !ok) {
			if (list.get(i).equals(v)) {
				ok = true;
			}
			i++;
		}
		return ok;
	}
	

	private Nodo obtenerNodoFinal(Problema p) {
		Nodo res = null;
		boolean encontrado = false;
		int pos = 0;
		while (pos < cerrados.size() && !encontrado) {
			if (cerrados.get(pos).getFila() == p.getFilaFinal() && cerrados.get(pos).getColumna() == p.getColumnaFinal()) {
				encontrado = true;
				res = cerrados.get(pos);
			}
			pos++;
		}
		return res;
	}

	

	public String mostrarCamino() {
		String camino = "BFS{";
		for (int i = 0; i < this.solucion.size(); i++) {
			camino += this.solucion.get(i).toString();
			if (i < this.solucion.size() - 1) {
				camino += "->";
			}
		}
		camino += "}";
		return camino;
	}

	public String mostrarMovimientos() {
		String movs = "BFS{";
		for (int i = 1; i < this.solucion.size(); i++) {
			movs += this.solucion.get(i).getGiro();
			if (i < this.solucion.size() - 1) {
				movs += "->";
			}
		}
		movs += "}";
		return movs;
	}
}
