#!/bin/bash
	if [ ! -f $1 ]
	then
		echo "$1>" no es un fichero
    else
		fecha=`date +%y%m%d`
		for i in $*
		do 			
			if [ -f "$i" ]
			then
				cp "$i" $fecha"_"$i
				echo backup de $1 realizada ">" $fecha"_"$i $fecha2
			fi
		done
	fi
    

