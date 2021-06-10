/*------------------------------------------------------------------------------
Proyecto Shell de UNIX. Sistemas Operativos
Grados I. Informatica, Computadores & Software
Dept. Arquitectura de Computadores - UMA

Algunas secciones est�n inspiradas en ejercicios publicados en el libro
"Fundamentos de Sistemas Operativos", Silberschatz et al.

Para compilar este programa: gcc ProyectoShell.c ApoyoTareas.c -o MiShell
Para ejecutar este programa: ./MiShell
Para salir del programa en ejecucion, pulsar Control+D
------------------------------------------------------------------------------*/

#include "ApoyoTareas.h" // Cabecera del m�dulo de apoyo ApoyoTareas.c

#define MAX_LINE 256 // 256 caracteres por l�nea para cada comando es suficiente
#include <string.h>  // Para comparar cadenas de cars. (a partir de la tarea 2)

#include <stdlib.h>
#include <stdio.h>



#define COLOR_RED     "\x1b[31m"
#define COLOR_GREEN   "\x1b[32m"
#define COLOR_YELLOW  "\x1b[33m"
#define COLOR_BLUE    "\x1b[34m"
#define COLOR_MAGENTA "\x1b[35m"
#define COLOR_CYAN    "\x1b[36m"
#define COLOR_RESET   "\x1b[0m"


job * procesos; //lista de procesos 



// --------------------------------------------
//              MANEJADOR DE SEÑALES       
// --------------------------------------------
void manejadorZombies(){
	//vamos recorriendo la lista de procesos (procesosBG), y vemos que ha pasado con cada uno de estos
	//cuando se activa la señal SIGCHLD, utilizando un waitpid con la opción WNOHANG para evitar bloquear al Shell

	int pid_wait, info, status; //pid, info. y status del proceso que vamos a esperar y a tratar
	enum status status_res;     //estado del proceso (SUSPENDIDO, REANUDADO, FINALIZADO)	
	//recorremos la lista
	for (int i=1;i<=list_size(procesos);i++){
		job * trabajo = get_item_bypos(procesos,i);  //trabajo de la lista en la posición i
		pid_wait = waitpid(trabajo->pgid,&status,  WUNTRACED | WNOHANG ); //obtenemos el pid del proceso por el que se envía la señal (pid no bloqueante)
		status_res = analyze_status(status,&info); //obtenemos el estado del proces
		if (trabajo->pgid == pid_wait){ 	
			block_SIGCHLD(); 
			if (status_res == SUSPENDIDO){
				//informamos que el proceso se ha suspendido
				trabajo->ground = DETENIDO;
				printf("\nComando "COLOR_CYAN"%s"COLOR_RESET" ejecutado en segundo plano con PID %d ha suspendido su ejecucion\n",trabajo->command,pid_wait);
			}else{
				//informamos que el proceso ha terminado y lo eliminamos de la lista de procesos
				printf("\nComando "COLOR_CYAN"%s"COLOR_RESET" ejecutado en segundo plano con PID %d ha concluido\n",trabajo->command,pid_wait);
				delete_job (procesos, trabajo); 
			}
			unblock_SIGCHLD(); 		  
		}
  	}	
}


// --------------------------------------------
//                     MAIN          
// --------------------------------------------

int main(void)
{
      char inputBuffer[MAX_LINE]; // Bufer que alberga el comando introducido
      int background;         // Vale 1 si el comando introducido finaliza con '&'
      char *args[MAX_LINE/2]; // La linea de comandos (de 256 cars.) tiene 128 argumentos como m�x
                              // Variables de utilidad:
      int pid_fork, pid_wait; // pid para el proceso creado y esperado
      int status;             // Estado que devuelve la funcion wait
      enum status status_res; // Estado procesado por analyze_status()
      int info;		      // Informacion procesada por analyze_status()
	  char path [2000];
	  char homePath[2000];

	 strcpy(path,getcwd(getenv("HOME"),2000)); 	//seleccionamos como el directorio principal donde está nuestro proyecto
     strcpy(homePath,getcwd(getenv("HOME"),2000)); //guardamos en una variable nuestro directorio principal
	 printf("=========================================================\n");
	 printf("Se utiliza como directorio HOME la ubicación del proyecto: %s\n",homePath);
	 printf("=========================================================\n");

     signal(SIGCHLD,manejadorZombies);
	 procesos = new_list("Lista de procesos"); //inicializamos la lista de procesos en segundo plano

      while (1) // El programa termina cuando se pulsa Control+D dentro de get_command()
      {   		

		ignore_terminal_signals(); //desactivamos las señales que llegan a la terminal (para evitar perder el control de la terminal)  
        printf(COLOR_GREEN"%s $$->"COLOR_RESET,path);
        fflush(stdout);
        get_command(inputBuffer, MAX_LINE, args, &background); // Obtener el proximo comando

        if (args[0]==NULL) continue; // Si se introduce un comando vacio, no hacemos nada
	
	//

	//tratamos el comando interno cd
	if (strcmp(args[0],"cd")==0){
		if (args[1] == NULL){ //nos movemos al directorio principal (donde tengamos nuestro el fichero alojado)
			if (strcmp(path,homePath)==0){	
				//lanzamos este mensaje si estamos ya en el directorio principal y ejecutamos el comando cd
				printf("Ya está en el directorio principal\n");
			}else {
				//nos movemos al directorio principal
				chdir(getenv("HOME"));
				getcwd(args[1],2000);
				strcpy(path,getcwd(args[1],2000));
			}
		}else if (chdir(args[1])==-1){
			//si el directorio al que queremos ir no existe, mostramos un mensaje de error
			printf(COLOR_RED"ERROR: No existe el directorio %s\n"COLOR_RESET,args[1]);
		}else if (strcmp(args[1],"..")){
				//nos movemos al directorio anterior
				chdir(getenv("cd .."));
				strcpy(path,getcwd(getenv("cd .."),2000)); 
		}else{
			//en cualquier otro caso, nos movemos al directorio indicado
			getcwd(args[1],2000);
			strcpy(path,getcwd(args[1],2000));
		}
	}else if (strcmp(args[0],"logout")==0){
		//comando "logout" para salir del shell
		printf(COLOR_CYAN"Saliendo del shell\n"COLOR_RESET);
		exit(EXIT_SUCCESS);

	 
	}else if (strcmp(args[0],"jobs")==0){
		//mostramos la lista de tareas activas en segundo plano
		print_job_list(procesos);
	
	}else if (strcmp(args[0],"fg")==0){
		job * trabajo;
		int p;
		if (args[1]==NULL){
			p = 1;
		}else {
			p = atoi(args[1]); //pasa una cadena de caracteres a entero
		}

		trabajo = get_item_bypos(procesos,p);
		if (trabajo == NULL){
			printf(COLOR_RED"La lista de procesos no contiene al trabajo %d\n"COLOR_RESET,p);
		}else{
			if (trabajo->ground==DETENIDO || trabajo->ground==SEGUNDOPLANO){
				
				set_terminal(trabajo->pgid);
				block_SIGCHLD();
				trabajo->ground = PRIMERPLANO;
				unblock_SIGCHLD();
				killpg(trabajo->pgid,SIGCONT);
				//esperamos a que termine
				printf("Comando "COLOR_CYAN"%s"COLOR_RESET" ejecutado en primer plano con pid %d\n",trabajo->command,trabajo->pgid);
				int pid = waitpid(trabajo->pgid,&status, WUNTRACED);
				status_res = analyze_status(status,&info);
				//devolvemos la terminal
				set_terminal(getpid());
				if (info==255 || info==SIGINT){ //hay que controlar si se fuerza la salida del proceso cuando se ejecuta fg
					if (info==SIGINT){ //Se ha pulsado Ctr-C
						printf("Se ha interrumpido la ejecución del comando "COLOR_CYAN"%s"COLOR_RESET" con pid %d. Info: %d\n",trabajo->command,trabajo->pgid,info);
					}
					job * item = get_item_bypid(procesos,pid);
					block_SIGCHLD();
					delete_job(procesos,item);
					unblock_SIGCHLD();
				}else {
					if (status_res!=FINALIZADO){
						block_SIGCHLD();
						trabajo->ground = DETENIDO;
						unblock_SIGCHLD();
						printf("Comando %s ejecutado en primer plano con pid %d. Estado Suspendido. Info: %d\n",trabajo->command,trabajo->pgid,info);
					}else{
						printf("Comando %s ejecutado en primer plano con pid %d. Estado Finalizado. Info: %d\n",trabajo->command,trabajo->pgid,info);
						block_SIGCHLD();
						delete_job(procesos,trabajo);
						unblock_SIGCHLD();
					}
				}
				
			}
		}

	}else if (strcmp(args[0],"bg")==0){
		int p; //posición del trabajo en la lista de procesos
		if (args[1] == NULL){
			p = 1;
		}else {
			p = atoi(args[1]); //pasa una cadena de caracteres a entero
		}

		job * trabajo = get_item_bypos(procesos,p);
		if (trabajo == NULL){
			printf(COLOR_RED"La lista de procesos no contiene al trabajo %d\n"COLOR_RESET,p);
		}else {
			if (trabajo->ground == DETENIDO){ //el proceso está detenido
				block_SIGCHLD();
				trabajo->ground = SEGUNDOPLANO;
				unblock_SIGCHLD();
				killpg(trabajo->pgid,SIGCONT);
				printf("Comando %s ha pasado a ejecutarse en segundo plano con pid %d\n",trabajo->command,trabajo->pgid);
			}
		}

	}  else {
		
    pid_fork = fork(); //creamos el hijo
	
	if (pid_fork < 0){ //ha ocurrido un error
	 	 fprintf(stderr,"Error en la creacion de procesos\n");

	}else if (pid_fork == 0) {//proceso hijo
	  	//asignamos un grupo propio al hijo
	  	new_process_group(getpid());
	
	 	 if (background == 0){ //es tarea en primer plano
							  //tomamos el terminal 
			set_terminal(getpid());
	  		}
	  	//reestablecemos las señales del terminal
	  	restore_terminal_signals(); 	
	 	//lanzamos el comando
     	execvp(args[0],args);
		
		//se ejecuta  SOLO si no se ha encontrado ningún comando con el nombre args[0]
		printf(COLOR_RED"Error. Comando "COLOR_CYAN"%s"COLOR_RED" no encontrado\n"COLOR_RESET,args[0]);
		exit(-1);
		
	}else{ //proceso padre
	        //si no ha ocurrido ningun error durante la ejecución del hijo, insertamos la tarea en la lista de procesos
			block_SIGCHLD();
			add_job(procesos,new_job(pid_fork,args[0],background==0?PRIMERPLANO:SEGUNDOPLANO));
			unblock_SIGCHLD();
        	if (background == 0){ //esta en primer plano
				//esperamos al hijo	
				pid_wait = waitpid(pid_fork,&status,WUNTRACED);
				//recuperamos la terminal
				set_terminal(getpid());
				//analizamos el estado
				status_res = analyze_status(status,&info);
				job * item = get_item_bypid(procesos,pid_fork);
	
				if (info==255 || info==SIGINT){ //controlamos si el proceso ha realizado un exit(-1). En ese caso, eliminamos el proceso mal lanzado de la lista de procesos
					block_SIGCHLD();			//controlamos también si se ha lanzado una señal SIGINT
					delete_job(procesos,item);
					unblock_SIGCHLD();
					if (info==SIGINT){
						printf("Se ha interrumpido la ejecución del comando "COLOR_CYAN"%s"COLOR_RESET" con pid %d. Info: %d\n",args[0],pid_fork,info);
					}
				}else{
					//imrimimos el estado del proceso pid_wait
					if (status_res == FINALIZADO){
						printf("Comando %s ejecutado en primer plano con pid %d. Estado Finalizado. Info: %d\n",args[0],pid_fork,info);
						block_SIGCHLD();
						delete_job(procesos,item);
						unblock_SIGCHLD();
					}else if (status_res == SUSPENDIDO){
						printf("Comando %s ejecutado en primer plano con pid %d. Estado Suspendido. Info: %d\n",args[0],pid_fork,info);
						block_SIGCHLD();
						item->ground = DETENIDO;
						unblock_SIGCHLD();
					}
				}
		}else{//esta en segundo plano
			printf("Comando %s ejecutado en segundo plano con pid %d\n",args[0],pid_fork);
		}
	 }
	}	
   }
} // end while



