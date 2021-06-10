#!/bin/bash
azul='\033[0;36m'
NC='\033[0m'
for i in *
do
	if [ -d $i ] 
	then
		echo -e ${azul}$i${NC} es un directorio
	fi

	if [ -f $i ] 
	then
		echo -e ${azul}$i${NC} es un fichero
	fi
done
