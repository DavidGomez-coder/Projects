rm -rf *.class
rm -rf ./ctdGenerator/*.class

echo ".class eliminados"

cup PLXC.cup 
jflex PLXC.flex
javac *.java
java PLXC ejemplos/$1.plx outs/$1.ctd

./ctd outs/$1.ctd