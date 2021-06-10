/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package vector;

import java.util.Iterator;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            return this.elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            int half = sz / 2;
            if (sz==1){
                elem = x;
                return new Unif<T>(elem);
            }else if (i< half){
                return new Node<>(new Unif<T>(elem).set(half, i, x), new Unif<T>(elem));
            }else{
                return new Node<>(new Unif<T>(elem), new Unif<T>(elem).set(half,i-half, x));
            }
        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {

            int half = sz / 2;
            if (i < half && this.left instanceof Unif) {
                return this.left.get(0,0);
            }else if (i > half && this.right instanceof Unif){
                return this.right.get(0,0);
            }else {
                //se trata de dos árboles, luego devolvemos el elemento de la derecha o izquierda
                //según el número del índice
                if (i < half){
                    return this.left.get(half, i);
                }else {
                    return this.right.get(half, i-half);
                }
            }
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            int half = sz / 2;
            if (i < half){
                this.left = this.left.set(half, i, x);
            }else{
                this.right = this.right.set(half, i-half, x);
            }
            return simplify();
        }

        protected Tree<T> simplify() {
            //comprobamos si son instancia de Unif
            if (this.left instanceof Unif && this.right instanceof Unif){
                //comprobamos indices
                if (this.left.get(0,0).equals(this.right.get(0,0))){
                    //mismo elemento,
                    return new Unif<>(this.left.get(0,0));
                }else{
                    return this;
                }
            }else {
                return this;
            }
        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        this.size = (int) Math.pow(2,n);
        root = new Unif<>(elem);
    }

    public int size() {
        return this.size;
    }

    public T get(int i) {
       return root.get(size, i);
    }

    public void set(int i, T x) {
        root = root.set(size, i, x);
    }

    @Override
    public Iterator<T> iterator() {
        return new SparseVectorIterator();
    }

                private class SparseVectorIterator implements Iterator<T> {
                    int act;

                    public SparseVectorIterator (){
                        act = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return act < size;
                    }

                    @Override
                    public T next() {
                        T elem = root.get(size, act);
                        act++;
                        return elem;
                    }
                }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }


}
