/**
 * Estructuras de Datos. Grado en Informática, IS e IC. UMA.
 * Examen de Febrero 2015.
 *
 * Implementación del TAD Deque
 *
 * Apellidos:
 * Nombre:
 * Grado en Ingeniería ...
 * Grupo:
 * Número de PC:
 */

package dataStructures.doubleEndedQueue;

import org.w3c.dom.DOMException;

public class LinkedDoubleEndedQueue<T> implements DoubleEndedQueue<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E x, Node<E> nxt, Node<E> prv) {
            elem = x;
            next = nxt;
            prev = prv;
        }
    }

    private Node<T> first, last;

    /**
     *  Invariants:
     *  if queue is empty then both first and last are null
     *  if queue is non-empty:
     *      * first is a reference to first node and last is ref to last node
     *      * first.prev is null
     *      * last.next is null
     *      * rest of nodes are doubly linked
     */

    /**
     * Complexity:
     */
    public LinkedDoubleEndedQueue() {
       first = null;
       last = null;
    }

    /**
     * Complexity: 1
     */
    @Override
    public boolean isEmpty() {
       return first==null && last==null;
    }

    /**
     * Complexity: 1
     */
    @Override
    public void addFirst(T x) {
        Node<T> newNode ;
        if (isEmpty()){
            newNode = new Node<>(x, null,null);
            first = newNode;
            last  = newNode;
        }else{
            newNode = new Node<>(x, first, null);
            first = newNode;
        }
    }

    /**
     * Complexity: 1
     */
    @Override
    public void addLast(T x) {
        Node<T> newNode;
        if (isEmpty()){
            newNode = new Node<>(x, null, null);
            first = newNode;
            last  = newNode;
        }else{
            newNode = new Node<>(x, null, last);
            last = newNode;
        }
    }

    /**
     * Complexity: 1
     */
    @Override
    public T first() {
       if (isEmpty())
           throw new EmptyDoubleEndedQueueException("No se puede extraer el primer elemento de una cola vacía");
       return first.elem;
    }

    /**
     * Complexity: 1
     */
    @Override
    public T last() {
        if (isEmpty())
            throw new EmptyDoubleEndedQueueException("No se puede extraer el último elemento de una cola vacía");
        return last.elem;
    }

    /**
     * Complexity: 1
     */
    @Override
    public void deleteFirst() {
        if (isEmpty())
            throw new EmptyDoubleEndedQueueException("No se puede eliminar un elemento de una cola vacía (FIRST)");

        //miramos si solo hay un elemento en la cola (si el siguiente del primero es null)
        if (first.next == null){
            //referenciamos first y last a null
            first = null;
            last = null;
        }else{
          first = first.next;
        }
    }

    /**
     * Complexity: 1
     */
    @Override
    public void deleteLast() {
        if (isEmpty())
            throw new EmptyDoubleEndedQueueException("No se puede eliminar un elemento de una cola vacía (LAST)");
        if (last.prev == null){ //solo hay un elemento
            first = null;
            last = null;
        }else{
            last = last.prev;
        }

    }

    /**
     * Returns representation of queue as a String.
     */
    @Override
    public String toString() {
    String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        String s = className+"(";
        for (Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
