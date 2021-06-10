/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de febrero de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */

package dataStructures.set;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.tuple.Tuple2;

import java.util.Objects;

public class DisjointSetDictionary<T extends Comparable<? super T>> implements DisjointSet<T> {

    private Dictionary<T, T> dic;

    /**
     * Inicializa las estructuras necesarias.
     */
    public DisjointSetDictionary() {
        dic = new AVLDictionary<>();
    }

    /**
     * Devuelve {@code true} si el conjunto no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        return dic.size() == 0;
    }

    /**
     * Devuelve {@code true} si {@code elem} es un elemento del conjunto.
     */
    @Override
    public boolean isElem(T elem) {
        return dic.isDefinedAt(elem);
    }

    /**
     * Devuelve el numero total de elementos del conjunto.
     */

    @Override
    public int numElements() {
        return dic.size();
    }

    /**
     * Agrega {@code elem} al conjunto. Si {@code elem} no pertenece al
     * conjunto, crea una nueva clase de equivalencia con {@code elem}. Si
     * {@code elem} pertencece al conjunto no hace nada.
     */
    @Override
    public void add(T elem) {
        if (!isElem(elem)){
            dic.insert(elem, elem);
        }
    }

    /**
     * Devuelve el elemento canonico (la raiz) de la clase de equivalencia la
     * que pertenece {@code elem}. Si {@code elem} no pertenece al conjunto
     * devuelve {@code null}.
     */
    private T root(T elem) {
        if (isElem(elem)){
            return getRootElem(elem);
        }
        return null;
    }

    private T getRootElem (T elem){
       T key = elem;
       T value = dic.valueOf(elem);
       while (!key.equals(value)){
           key = value;
           value = dic.valueOf(key);
       }
        return key;
    }

    /**
     * Devuelve {@code true} si {@code elem} es el elemento canonico (la raiz)
     * de la clase de equivalencia a la que pertenece.
     */
    private boolean isRoot(T elem) {
        return dic.valueOf(elem).equals(elem);
    }

    /**
     * Devuelve {@code true} si {@code elem1} y {@code elem2} estan en la misma
     * clase de equivalencia.
     */
    @Override
    public boolean areConnected(T elem1, T elem2) {

        return isElem(elem1) && isElem(elem2) && Objects.equals(root(elem1), root(elem2));
    }

    /**
     * Devuelve una lista con los elementos pertenecientes a la clase de
     * equivalencia en la que esta {@code elem}. Si {@code elem} no pertenece al
     * conjunto devuelve la lista vacia.
     */
    @Override
    public List<T> kind(T elem) {
        List<T> kind = new ArrayList<>();
        if (isElem(elem)){
            //recorremos todas las claves, y vemos si pertenecen a la misma clase de equivalencia
            for (T key : dic.keys()){
                if (areConnected(key, elem)){
                    kind.append(key);
                }
            }
        }
        return kind;
    }

    /**
     * Une las clases de equivalencias de {@code elem1} y {@code elem2}. Si
     * alguno de los dos argumentos no esta en el conjunto lanzara una excepcion
     * {@code IllegalArgumenException}.
     */
    @Override
    public void union(T elem1, T elem2) {
        if (!isElem(elem1))
            throw new IllegalArgumentException("El elemento '" + elem1 + "' no está en el conjunto");

        if (!isElem(elem2))
            throw new IllegalArgumentException("El elemento '" + elem2 + "' no está en el conjunto");

        T canonical1 = root(elem1);
        T canonical2 = root(elem2);

        if (canonical1!=null && canonical2 != null) {
            if (canonical1.compareTo(canonical2) > 0) { //el primero es mayor
                dic.insert(canonical1, canonical2);
            } else {
                dic.insert(canonical2, canonical1);
            }
        }
    }

    // ====================================================
    // A partir de aqui solo para alumnos a tiempo parcial
    // que no sigan el proceso de evaluacion continua.
    // ====================================================

    /**
     * Aplana la estructura de manera que todos los elementos se asocien
     * directamente con su representante canonico.
     */
    @Override
    public void flatten() {
        for (T key : dic.keys()){
            T canonical = root(key);
            dic.insert(key, canonical);
        }
    }

    /**
     * Devuelve una lista que contiene las clases de equivalencia del conjunto
     * como listas.
     */
    @Override
    public List<List<T>> kinds() {
        List<List<T>> kinds = new ArrayList<>();
        //lista de valores diferentes
        List<T> values = new ArrayList<>();
        for (T val : dic.values()){
            if (!isElem(val, values)){
                values.append(val);
            }
        }

        //recorremos la lista de valores y añadimos sus clases de equivalencia
        for (T val : values){
            List<T> kind = new ArrayList<>();
            for (Tuple2<T,T> kV : flattenKinds().keysValues()){
                if (kV._2().equals(val)){
                    kind.append(kV._1());
                }
            }
            kinds.append(kind);
        }
        return kinds;
    }

    private boolean isElem (T val, List<T> values){
        int  i = 0;
        while (i< values.size() && !values.get(i).equals(val)){
            i++;
        }
        return i< values.size();
    }

    private Dictionary<T,T> flattenKinds (){
        Dictionary<T,T> newDic = new AVLDictionary<>();
        for (T key : dic.keys()){
            T canonical = root(key);
            newDic.insert(key, canonical);
        }
        return newDic;
    }

    /**
     * Devuelve una representacion del conjunto como una {@code String}.
     */
    @Override
    public String toString() {
        return "DisjointSetDictionary(" + dic.toString() + ")";
    }
}
