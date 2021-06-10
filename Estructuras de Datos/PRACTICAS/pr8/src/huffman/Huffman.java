package huffman;
/**
 * Huffman trees and codes.
 * <p>
 * Data Structures, Grado en Informatica. UMA.
 * <p>
 * <p>
 * Student's name: David Gómez Pérez
 * Student's group: A
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.searchTree.AVL;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1  
    public static Dictionary<Character, Integer> weights(String s) {
        Dictionary<Character, Integer> w_dict = new AVLDictionary<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            int weight;
            if (!w_dict.isDefinedAt(c)) {
                weight = 1;
            } else {
                weight = w_dict.valueOf(c) + 1;
            }
            w_dict.insert(c, weight);
        }
        return w_dict;
    }

    // Exercise 2.a 
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
        PriorityQueue<WLeafTree<Character>> leaves = new LinkedPriorityQueue<>();
        Dictionary<Character, Integer> weights = weights(s);
        for (Tuple2<Character, Integer> tuple : weights.keysValues()) {
            WLeafTree<Character> leaf = new WLeafTree<>(tuple._1(), tuple._2());
            leaves.enqueue(leaf);
        }
        return leaves;
    }

    // Exercise 2.b  
    public static WLeafTree<Character> huffmanTree(String s) {
        if (!twoDifferentCharacters(s))
            throw new HuffmanException("La cadena tiene que tener al menos dos caracters distintos");

        PriorityQueue<WLeafTree<Character>> leaves = huffmanLeaves(s);
        while (true) {
            WLeafTree<Character> first = leaves.first();
            leaves.dequeue();
            //miramos si la cola de prioridad está vacía
            if (leaves.isEmpty()) {
                //devolvemos el árbol completo (first)
                return first;
            }
            //extraemos el segundo
            WLeafTree<Character> second = leaves.first();
            leaves.dequeue();
            //creamos nuevo árbol
            WLeafTree<Character> newTree = new WLeafTree<>(first, second);
            //añadimos a la cola de prioridad
            leaves.enqueue(newTree);
        }
    }

    private static boolean twoDifferentCharacters(String s) {
        if (s.length()<2)
            return false;

        char p = s.charAt(0);
        int i = 1;
        while (i<s.length() && s.charAt(i)==p){
            i++;
        }

        return i < s.length();
    }

    // Exercise 3.a 
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        Dictionary<Character, List<Integer>> union_dict = new AVLDictionary<>();
        //añadimos diccionario d1
        for (Character c : d1.keys()) {
            union_dict.insert(c, d1.valueOf(c));
        }
        //añadimos diccionario d2
        for (Character c : d2.keys()) {
            union_dict.insert(c, d2.valueOf(c));
        }
        return union_dict;
    }

    // Exercise 3.b  
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        Dictionary<Character, List<Integer>> newDict = d;
        for (Character c : d.keys()) {
            List<Integer> list = d.valueOf(c);
            list.prepend(i);
            d.insert(c, list);
        }
        return newDict;
    }

    // Exercise 3.c  
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        if (ht.isLeaf()){
            Dictionary<Character, List<Integer>> dict = new AVLDictionary<>();
            List<Integer> list = new ArrayList<>();
            dict.insert(ht.elem(), list);
            return dict;
        }else{
            return joinDics(prefixWith(0, huffmanCode(ht.leftChild())),
                            prefixWith(1, huffmanCode(ht.rightChild())));
        }
    }

    // Exercise 4  
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        List<Integer> res = new ArrayList<>();
        for (Character c : s.toCharArray()){
            List<Integer> value = hc.valueOf(c);
            for (Integer i : value){
                res.append(i);
            }
        }
        return res;
    }

    // Exercise 5 
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        List<Integer> aux_list = bits;
        WLeafTree<Character> treeAux = ht;
        String res = "";
        while(!aux_list.isEmpty()){
            //guardamos y eliminamos el primer elemento
            Integer i = aux_list.get(0);
            aux_list.remove(0);
            if (i == 0){
                treeAux = treeAux.leftChild();
            }else{
                treeAux = treeAux.rightChild();
            }
            if (treeAux.isLeaf()){
                //guardamos el valor de la hoja en el resultado
                res+=treeAux.elem();
                //restauramos el valor del árbol
                treeAux = ht;
            }
        }
        return res;
    }
}
