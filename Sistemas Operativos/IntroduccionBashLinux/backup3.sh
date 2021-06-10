#!/bin/bash
#GUARDAMOS LA FECHA
fecha=`date +%y%m%d`
rojo='\033[0;31m'
azul='\033[0;36m'
NC='\033[0m'

#SI EL DIRECTORIO BACKUP NO ESTÁ CREADO, LO CREAMOS
if [ ! -d backup ]
then
    echo -e "Se ha creado el directorio ${azul}backup${NC}"
    mkdir backup
fi


if [ ! -d backup"/"$fecha ]                     #MIRAMOS SI ESTÁ CREADO EL DIRECTORIO CORRESPONDIENTE A LA FECHA 
then                                            #SI ESTÁ CREADO NO HACEMOS NADA, SINO, LO CREAMOS
    echo -e "Se ha creado el directorio ${azul}backup/$fecha${NC}"
    mkdir backup"/"$fecha   
fi


#PARA TODOS LOS FICHEROS INTRODUCIDOS
for i in $*
do
    if [ -f "$i" ]   #SI ES UN FICHERO 
    then
       cp "$i" backup"/"$fecha"/"$i        #COPIAMOS EL ARCHIVO $i en LA RUTA DEL BACKUP
    else
        echo -e "${rojo}$i${NC} no es un fichero" #LANZAMOS UN MENSAJE SI NO SE PUEDE CREAR LA COPIA (NO ES UN FICHERO)
    fi



done
echo "====================================================="
#LANZAMOS UN MENSAJE DEL DIRECTORIO DEL BACKUP
echo Copia de Seguridad guardada en " ==>" $PWD"/"backup"/"$fecha
    


