

import java.util.Arrays;
import java.util.Random;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;

import Problema.*;



/**
 * 
 * @date   2018-03-22
 * 
 * Plantilla para la pr�ctica de algoritmos de b�squeda con Robocode (G. Ing. Comp.)
 * 
 * 
 */

public class RouteFinder {

	public static void main(String[] args) throws ProblemaException {
		
		
		
		//Creamos un mapa con los datos especificados
		// Create the battlefield
		
		int numPixelFila= 800;
		int numPixelCol=600;
		int tamCelda = 50;       //celdas de 50 x 50
		
		//n�mero de obst�culos
		////DEBER� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int numObstaculos = 40;
		
		//semilla para el generador de n�meros aleatorios
		//DEBER� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		long semilla = 123456;  
		
		//tama�o del mapa de obst�culos
		//DEBER� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int nFil = numPixelFila / tamCelda;
		int nCol = numPixelCol  / tamCelda;
		


		//AQU� SE DEBER�A DE GENERAR EL MAPA DE OBST�CULOS Y LAS POSICIONES INICIAL Y FINAL DEL ROBOT
		//
		//
		Problema mapa = new Problema(semilla, nFil, nCol, numObstaculos);
		//
		//
		
		
		
		
		
		
		// Crear el RobocodeEngine desde una instalaci�n en C:/Robocode
		RobocodeEngine engine =
				new RobocodeEngine(new java.io.File("C:/Robocode"));
		// Mostrar el simulador de Robocode
		engine.setVisible(true);
		
		
		
		BattlefieldSpecification battlefield =
				new BattlefieldSpecification(numPixelFila, numPixelCol);
		// Establecer par�metros de la batalla
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		

		
		// En modelRobots recogemos la especificaci�n de los robots que utilizaremos en la simulaci�n.
		// En este caso ser�n un robot sittingDuck y nuestro propio robot. La referencia a nuestro robot
		// debe ser relativa al proyecto que pusimos en Options>Preferences>DevelopmentOptions en Robocode,
		// indicando el n�mbre del paquete (si lo hay) y del robot.  En nuestro caso suponemos como nombre 
		// prueba.Bot3*
		// Al nombre de los robots definidos por el usuario siempre hay que a�adirle el car�cter * al final. 
		RobotSpecification[] modelRobots =
				engine.getLocalRepository
				("sample.SittingDuck,misRobots.R2D2*");
		
		// Incluiremos un robot sittingDuck por obst�culo, m�s nuestro propio robot.
		RobotSpecification[] existingRobots =
				new RobotSpecification[numObstaculos+1];
		RobotSetup[] robotSetups = new RobotSetup[numObstaculos+1];
		
		/*
	     * Creamos primero nuestro propio robot y lo colocamos en la posici�n inicial del problema,
	     * que deber� estar libre de obst�culo.
		 */
		
		int indice = 0;
		
		existingRobots[indice]=modelRobots[1];

				robotSetups[indice]=new RobotSetup((double) mapa.getFilaInicial()*50 + 25,        //AQU� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE A LA POSICI�N INICIAL DEL ROBOT
				                            (double) mapa.getColumnaInicial()*50 + 25         ,        //AQU� DEBE FIGURAR LA COLUMNA EN PIXELS CORRESPONDIENTE A LA POSICI�N INICIAL DEL ROBOT
										   0.0);              //orientaci�n inicial

		
		/*
	     * Creamos un robot sittingDuck por cada obst�culo, y lo colocamos en el centro de la 
	     * celda correspondiente.
		 */
		
		
		
		//AQU� SE DEBER�A CREAR UN ROBOT sittingDuck EN LA POSICI�N DE CADA OBST�CULO DE LA MALLA.
		//
		//
		//  ...
		//
		//
		
		indice++;
		for (int f = 0; f < nFil; f++){
			for (int c = 0; c < nCol; c++){
				if (mapa.getMapa()[f][c] == 1){//SI HAY OBST�CULO EN ESA POSICI�N DE LA MALLA
					
					existingRobots[indice]=modelRobots[0];   //sittingDuck
					
					
					robotSetups[indice]=  new RobotSetup(  (double) f*50 + 25,   //AQU� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA CELDA DONDE HAY OBST�CULO
							                               (double) c*50 + 25,   //AQU� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA COLUMNA DONDE HAY OBST�CULO
							                             0.0);    //orientaci�n
					indice++;
					
				}//if mapa
			}//for c
		}//for f
			
		System.out.println("Generados " + (indice -1) + " sitting ducks.");
					

				
		
		/* 
		 * Crear y desarrollar la batalla con los robots antes definidos
		 */
		BattleSpecification battleSpec =
				new BattleSpecification(battlefield,
						numberOfRounds,
						inactivityTime,
						gunCoolingRate,
						sentryBorderSize,
						hideEnemyNames,
						existingRobots,
						robotSetups);
		
		
		// Ejecutar la simulaci�n el tiempo especificado
		engine.runBattle(battleSpec, true); 
		// Cerrar la simulaci�n
		engine.close();
		// Asegurarse de que la MV de Java se cierra adecuadamente.
		System.exit(0);
		

	}
	
}