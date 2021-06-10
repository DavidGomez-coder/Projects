#!/bin/bash
#Comprobamos si el argumento que le pasamos es o no un directorio
if [ -d $1 ]
then
    echo $1 es un directorio
else
    echo $1 no es un directorioo
fi
