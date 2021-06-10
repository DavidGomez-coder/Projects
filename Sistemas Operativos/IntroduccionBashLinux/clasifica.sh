#!/bin/bash

rojo='\033[0;31m'
azul='\033[0;36m'
verde='\033[0;32m'
NC='\033[0m'

#MIRAMOS SI EXISTE EL DIRECTORIO $1
if [ ! -d $1 ]
then
    echo -e "${rojo}El directorio $1 no existe${NC}"

else
    #SI NO EXISTE EL DIRECTORIO $2, LO CREAMOS
    if [ ! -d $2 ]
    then
        mkdir $2
        echo -e "Se ha creado el directorio $PWD/${azul}$2${NC}"
    fi    

    		
    #PARA TODOS LOS ARCHIVOS MP3
    for i in $1*.mp3
    do
        lin_autor=`grep autor:* $i`    #OBTENEMOS LA LINEA DEL AUTOR
        lin_titulo=`grep titulo:* $i`  #OBTENEMOS LA LINEA DEL TITULO
        
        autor=${lin_autor:6}           #RECORTAMOS EL NOMBRE DEL AUTOR
        titulo=${lin_titulo:7}         #RECORTAMOS EL NOMBRE DEL TITULO
        
        #MIRAMOS SI EXISTE EL DIRECTORIO CON EL NOMBRE DEL AUTOR. SI NO EXISTE, LO CREAMOS
        if [ ! -d $2"/"$autor ]
        then
            mkdir $2"/"$autor
            echo -e "Se ha creado el directorio $PWD/${azul}$2/$autor${NC}"
        fi

        #HACEMOS UNA COPIA EN EL DIRECTORIO $2"/"$autor DE LA CACIÓN 
        cp $i $2"/"$autor"/"$titulo.mp3
         

    done
fi
echo "----------------------------------"
echo -e "${verde}Clasificacion realizada con éxito${NC}"
