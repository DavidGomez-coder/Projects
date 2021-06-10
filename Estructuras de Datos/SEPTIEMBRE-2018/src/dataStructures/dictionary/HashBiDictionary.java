package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		return bKeys.isEmpty() && bValues.isEmpty();
	}
	
	public int size() {
		return bKeys.size();
	}
	
	public void insert(K k, V v) {
		if (bKeys.isDefinedAt(k)){
			V value = bKeys.valueOf(k);
			bKeys.delete(k);
			bValues.delete(value);
			insert(k,v);
		}else if (bValues.isDefinedAt(v)){
			K key = bValues.valueOf(v);
			bValues.delete(v);
			bKeys.delete(key);
			insert(k,v);
		}else{
			bKeys.insert(k,v);
			bValues.insert(v,k);
		}
	}
	
	public V valueOf(K k) {
		return bKeys.valueOf(k);
	}
	
	public K keyOf(V v) {
		return bValues.valueOf(v);
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		if (isDefinedKeyAt(k)){
			V v = bKeys.valueOf(k);
			bKeys.delete(k);
			bValues.delete(v);
		}
	}
	
	public void deleteByValue(V v) {
		if (isDefinedValueAt(v)){
			K k = bValues.valueOf(v);
			bValues.delete(v);
			bKeys.delete(k);
		}
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}

	private static <K,V extends Comparable<? super V>> boolean  isInyective (Dictionary<K, V> dict){
		//teramos sobre los valores del diccionario y contamos cuantos valores NO REPETIDOS hay, tendremos que
		// el diccionario es biyectivo si el número de valores no repetidos coincide con el número de claves
		Dictionary<V, Integer> nValues = new HashDictionary<>();
		Iterable<V> values = dict.values();
		for (V v : values){
			int times;
			if (nValues.isDefinedAt(v)){
				times = nValues.valueOf(v);
				times++;
			}else{
				times = 1;
			}
			nValues.insert(v, times);
		}

		int nRep = 0;
		//miramos cuantos valores tienen solo una repeticion
		for (Tuple2<V,Integer> tuple : nValues.keysValues()){
			if (tuple._2() == 1){
				nRep++;
			}
		}

		return nRep == dict.size();
	}
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		if (!isInyective(dict))
			throw new IllegalArgumentException("Dictionary 'dict' isn't inyective");

		BiDictionary<K,V> res = new HashBiDictionary<>();
		for (Tuple2<K,V> tuple : dict.keysValues()){
			res.insert(tuple._1(), tuple._2());
		}

		return  res;
	}

	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		BiDictionary<K,W> res = new HashBiDictionary<>();
		for (Tuple2<K,V> kV : this.keysValues()){
			K k = kV._1();
			V v = kV._2();
			if (bdic.isDefinedKeyAt(v)){
				W w = bdic.valueOf(v);
				res.insert(k, w);
			}
		}
		return res;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		boolean ok = true;
		Iterator<K> it = bd.keys().iterator();
		while (it.hasNext() && ok){
			K k1 = it.next();
			if (!isElem(k1, bd.values())){
				ok = false;
			}
		}

		return ok;
	}

	private static <K extends Comparable<? super K>> boolean isElem (K k, Iterable<K> values){
		boolean ok = false;
		Iterator<K> it = values.iterator();
		while (it.hasNext() && !ok){
			if (it.next().equals(k)){
				ok = true;
			}
		}
		return ok;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		if (!isPermutation(bd))
		    throw new IllegalArgumentException("El diccionario pasado como parámetro no es una permutación");


		List<K> orbit = new ArrayList<>();
		K value = bd.valueOf(k);
		K key = k;
		while (true){
		    if (value.equals(k)){
		        orbit.prepend(k);
		        break;
            }else{
		        orbit.append(value);
		        key = value;
		        value = bd.valueOf(key);
            }
        }
		return  orbit;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		if (!isPermutation(bd))
		    throw new IllegalArgumentException("El diccionario pasado como parámetro no es una permutación");

		List<List<K>> orbits = new ArrayList<>();
		Set<K> S =  keys(bd);
        K k = S.iterator().next();
        S.delete(k);
        //k es la primera órbita a calcular
        while (true){
                List<K> orbit = orbitOf(k, bd);
                //eliminamos los elementos de S que están en orbit
                for (K k2 : orbit){
                    if (S.isElem(k2)){
                        S.delete(k2);
                    }
                }
                orbits.append(orbit);
                if (!S.isEmpty()) {
                    k = S.iterator().next();
                }else{
                    return orbits;
                }

        }

	}

	private  static  <K extends Comparable<? super K>> Set<K> keys (BiDictionary<K, K> bd){
	    Set<K> set = new AVLSet<>();
	    for (K k : bd.keys()){
	        set.insert(k);
        }
	    return set;
    }

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
