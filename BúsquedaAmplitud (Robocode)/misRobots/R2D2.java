package misRobots;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import Busqueda.BreadthFirstSearch;
import Busqueda.BreadthFirstsSearchException;
import Problema.Nodo;
import Problema.Problema;
import Problema.ProblemaException;
import robocode.Robot;
import robocode.util.*;

public class R2D2 extends Robot {


	int numPixelFila= 800;
	int numPixelCol=600;
	int tamCelda = 50;       //celdas de 50 x 50
	
	//número de obstáculos
	int numObstaculos = 40;
	
	//semilla para el generador de números aleatorios
	long semilla = 123456;  
	
	//tamaño del mapa de obstáculos
	int nFil = numPixelFila / tamCelda;
	int nCol = numPixelCol  / tamCelda;
	
	Problema problema;
	BreadthFirstSearch bfs;
	
	int filaInicial, columnaInicial, filaFinal, columnaFinal;
	
    public void run() {
        setColors(Color.BLUE, Color.WHITE, Color.CYAN);
        try {
        	problema = new Problema(semilla, nFil, nCol, numObstaculos);
        	filaInicial = problema.getFilaInicial();
        	columnaInicial = problema.getColumnaInicial();
        	filaFinal = problema.getFilaFinal();
        	columnaFinal = problema.getColumnaFinal();
        	
            bfs = new BreadthFirstSearch(problema);
        	
            System.out.println("Posición Inicial : ["+filaInicial+","+columnaInicial+"]");
            System.out.println("Posición Final : ["+filaFinal+","+columnaFinal+"]");
            System.out.println("Seguimiento del robot:");
            System.out.println("***********************************************");
            
        	List<Nodo> solucion = bfs.getSolucion();
        	System.out.println("Empezamos en: " + solucion.get(0).toString());
        	for (int i=1;i<solucion.size();i++) {
        		int grados = obtenerGrados(solucion.get(i).getGiro());
        		turnRight(robocode.util.Utils.normalRelativeAngleDegrees(grados-getHeading()));
        		if (grados == 90 || grados == 180  || grados==270 || grados==0) {
        			ahead(tamCelda);
        		}else {
        			double mitadCelda = tamCelda/2;
        			ahead(2*Math.sqrt(Math.pow(mitadCelda, 2)+Math.pow(mitadCelda, 2)));
        		}
        		//imprimimos la posición del robot
    			System.out.println("Llegamos a " + solucion.get(i).toString() + " con movimiento "+solucion.get(i).getGiro());
        	}
        	
        	//imprimimos numero de nodos abiertos y cerrados
        	System.out.println("Nodos abiertos: " + bfs.getNumNodosAbiertos() + "    " + "Nodos Cerrados: " + bfs.getNumNodosExplorados() + "     "+ 
        			"Coste Camino: " + bfs.getPathCost());
        	
        }catch (ProblemaException | BreadthFirstsSearchException e) {
        	System.err.println("Error: " + e.getLocalizedMessage());
        }
    }
    
 
    
    private int obtenerGrados (String movimiento) {
    	int grados;
    	switch (movimiento) {
    	case "ARRIBA" : grados = 0; break;
    	case "ABAJO" : grados = 180; break;
    	case "DERECHA" : grados = 90; break;
    	case "IZQUIERDA" : grados = 270; break;
    	case "D_IZQ_ARRIBA" : grados = 315; break;
    	case "D_DER_ARRIBA" : grados = 45; break;
    	case "D_IZQ_ABAJO" : grados = 225; break;
    	default  : grados = 135; 
    	}
    	return grados;
    }
    
    public void onPaint(Graphics2D g) {
    	//pintamos las cuadriculas
    	for (int i=0;i<problema.getNumFilas();i++) {
    		for (int j=0;j<problema.getNumColumnas();j++) {
    			 g.setColor(Color.WHITE);
    	            g.drawRect(i*tamCelda,j*tamCelda, 50, 50);
    		}
    	}
    	
    	
        //pintamos nodos abiertos
        for (int i=0;i<bfs.getAbiertos().size();i++){
            g.setColor(Color.white);
            g.fillRect(bfs.getAbiertos().get(i).getFila()*tamCelda+25,bfs.getAbiertos().get(i).getColumna()*tamCelda+25,10,10);
        }

        //pintamos nodos cerrados
        for (int i=0;i<bfs.getCerrados().size();i++){
            g.setColor(Color.ORANGE);
            g.fillRect(bfs.getCerrados().get(i).getFila()*tamCelda+25,bfs.getCerrados().get(i).getColumna()*tamCelda+25,10,10);
           
        }

        //pintamos nodos camino
        for (int i=1;i<bfs.getSolucion().size()-1;i++){
            g.setColor(Color.CYAN);
            g.fillRect(bfs.getSolucion().get(i).getFila()*tamCelda+25,bfs.getSolucion().get(i).getColumna()*tamCelda+25,10,10);
        }

        // Inicial
        g.setColor(Color.GREEN);
        g.fillRect((int)filaInicial*tamCelda +25,(int) columnaInicial*tamCelda +25, 10, 10);


        // DESTINO
        g.setColor(Color.RED);
        g.fillRect((int) filaFinal*tamCelda +25,(int) columnaFinal*tamCelda +25, 10, 10);
        
        //Leyenda con los datos
        FontMetrics fm = g.getFontMetrics();
        Font fuente = new Font("ARIAL",Font.BOLD,14);
        g.setFont(fuente);
        
        g.setColor(Color.WHITE);
        String abiertos = "NODOS ABIERTOS: " + bfs.getNumNodosAbiertos();
        g.drawString(abiertos, 0, 0);
        g.setColor(Color.ORANGE);
        String cerrados = "NODOS CERRADOS: " + bfs.getNumNodosExplorados();
        g.drawString(cerrados, 0, abiertos.length() + 5);
        g.setColor(Color.CYAN);
        String camino = "COSTE CAMINO: " + bfs.getPathCost();
        g.drawString(camino, 0, cerrados.length() + abiertos.length() +10);

    }
}
