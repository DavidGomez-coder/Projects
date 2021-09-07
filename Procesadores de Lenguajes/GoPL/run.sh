rm -rf *.class
rm -rf ./instructions/*.class
echo ".class eliminados"

cup GoPL.cup 
jflex GoPL.flex
javac *.java

for i in $(ls ejemplos/*.gopl)
do
    echo -e "\n========= $i =========\n"
    java GoPL $i
    echo -e "\n\n"
done
#java GoPL ejemplos/$1.gopl #outs/$1.ctd
