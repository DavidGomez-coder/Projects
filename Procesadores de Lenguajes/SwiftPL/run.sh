#borramos los archivos .class
sudo rm -rf *.class
sudo rm -rf ./instructions/*.class
echo "archivos .class borrados"

#compilamos de nuevo
cup SwiftPL.cup 
jflex SwiftPL.flex
javac *.java
java SwiftPL ejemplos/$1.SwiftPL #outs/$1.ctd
