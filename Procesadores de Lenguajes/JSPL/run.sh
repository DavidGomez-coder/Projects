rm -rf *.class
rm -rf ./instructions/*.class
echo ".class eliminados"

cup JSPL.cup 
jflex JSPL.flex
javac *.java
java JSPL ejemplos/$1.JSPL #outs/$1.ctd
