/********************************************************************
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 4ª. Ejercicio 12.b de la tercera relación
 *              Implementar el TAD Bolsa en java
 *
 * (completa y sustituye los siguientes datos)
 * Titulación: Grado en Ingeniería [Informática | del Software | de Computadores].
 * Alumno: APELLIDOS, NOMBRE
 * Fecha de entrega:  DIA | MES | AÑO
 ********************************************************************
 */

package dataStructures.bag;

import java.util.StringJoiner;

public class SortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

	static private class Node<E> {
		E elem;
		int count;
		Node<E> next;

		Node(E x, int n, Node<E> node) {
			elem = x;
			count = n;
			next = node;
		}
	}

	private Node<T> first; // Mantener esta lista enlazada ordenada por "elem"

	public SortedLinkedBag() {
		first = null;
	}

	/**
	 * Devuelve si el bag esta vacio
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Inserta un elemento en el bag
	 * Si ya esta, incrementa su contador
	 * en otro caso, lo incluye con contador 1
	 */
	public void insert(T item) {
		Node<T> previous = null;
		Node<T> current = first;

		//vamos recorreiendo la lista mientras que el elemento sea distinto y menor que el dado
		while (current != null && current.elem.compareTo(item) < 0 ) {
			previous = current;
			current = current.next;
		}
		if (current!=null && current.elem.equals(item)){
			current.count++;
		}else if (previous == null){
			first = new Node<>(item,1,first);
		}else {
			previous.next = new Node<>(item,1,previous.next);
		}
	}
	/**
	 * Devuelve las ocurrencias de "item".
	 * Devuelve 0 si no esta
	 */
	public int occurrences(T item) {
		Node<T> current = first;
		int result = 0;
        while (current!=null && current.elem.compareTo(item)<0){
        	current = current.next;
		}
        if (current!=null && current.elem.equals(item)){
        	result = current.count;
		}
		return result;
	}

	/**
	 * Elimina "item" del bag.
	 * Si aparece mas de una vez se decrementa el contador
	 * Si solo apercce una vez se elimina
	 * Si no esa, no se hace nada
	 */
	public void delete(T item) {
		Node<T> previous = null;
		Node<T> current = first;

		//buscamos el elemento
		while (current!=null && current.elem.compareTo(item)<0){
			previous = current;
			current = current.next;
		}
		//si el elemento está en la lista lo eliminamos, sino no hacemos nada
		if (current!=null && current.elem.equals(item)) {
			//si lo encontramos, lo eliminamos
			if (current.count > 1) {
				current.count--;
			} else if (current.count == 1){
				if (previous!=null) {
					//el siguiente del previo es el siguiente de current
					previous.next = current.next;
				}else{
					first = current.next;
				}
			}
		}
	}

	/**
	 * Deuelve una representación textual del bag
	 */
	public String toString() {
        String className = getClass().getSimpleName();
		StringJoiner sj = new StringJoiner(",", className+"(", ")");
		for (Node<T> p = first; p != null; p = p.next) {
			sj.add("(" + p.elem + ", " + p.count + ") ");
		}
		return sj.toString();
	}
}
