#!/bin/bash
if [ ! -f "$1" ] #Chequea si el parámetro de entrada es un fichero que existe
then      #Si no existe, lanza un mensaje de error
    echo $1 no existe
else     #En caso de existir
    A=$(ls $1* | wc -w) #Lista los ficheros que tienen el mismo nombre que el #fichero de entrada, y cuenta cuántos hay. Este número lo almacena en A
    if [ $A -ge 9 ] #Si hay 9 o más da un mensaje de que no se pueden crear mas
    then
        echo "Se ha superado el número máximo de versiones"
    else
        Num=`expr $A + 1` #Se incrementa en 1 el número de copias 
        Version=$1.$Num   #Se crea un nuevo fichero con el nombre de la version
        cp $1 $Version    #Copiamos el fichero antiguo al nuevo
    fi
fi
