#!/bin/bash
for i in *   #Para cada entrada "i" del directorio donde se ejecuta el script
do
	if [ -d $i ] #Chequea si la entrada es un directorio
	then
		FILES=`ls -l $i | wc -l`
		FILES2=`expr $FILES - 1` #Lista el contenido y cuenta el número de lineas
        # Le restamos uno puesto que una de las líneas esel#número de bloques que ocupa el directorio# si el directorio esta vacíowc da como resultado 0 y al restarle 1 FILES2 valdrá-1 en este caso, reasignamos FILES2 a su valor correcto que es 0
			if [ $FILES2 -eq -1 ] 
			then
			FILES2=0
			fi
	echo $i: $FILES2
	fi
done
