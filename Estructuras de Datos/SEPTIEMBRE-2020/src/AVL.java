/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;
import java.util.Objects;
import java.util.zip.InflaterInputStream;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        this.remainingCapacity = initialCapacity;
        this.weights = new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return this.remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if (weight > remainingCapacity)
            throw new IllegalArgumentException ("La capacidad del cubo es menor que la del nuevo objeto a insertar");

        weights.prepend(weight);
        this.remainingCapacity -=weight;
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        return this.weights;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            this.height = 1 + Math.max(height(left), height(right));
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            this.maxRemainingCapacity = Math.max(bin.remainingCapacity(),
                    Math.max(maxRemainingCapacity(left), maxRemainingCapacity(right)));
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            if (height(right) - height(left) > 1) {
                Node rt = this.right;
                Node rlt = rt.left;

                this.right = rlt;
                this.setHeight();

                rt.left = this;
                rt.setHeight();

                return rt;
            }else {
                this.setHeight();
                this.setMaxRemainingCapacity();
                return this;
            }
        }
    }

    private static int height(Node node) {
        if (node!=null)
            return node.height;

        return 0;
    }

    private static int maxRemainingCapacity(Node node) {
        if (node!=null)
            return node.maxRemainingCapacity;

        return 0;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    private boolean isEmpty (Node node){
        return node == null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        root = addNewBinRec(bin, root);
    }

    private Node addNewBinRec (Bin bin, Node node){
        if (isEmpty(node)){
            node = new Node();
            node.bin = bin;
        }else{
            node.right = addNewBinRec(bin, node.right);
        }

        return node.rotateLeft();

    }



    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        root = addFirstRec(initialCapacity, weight, root);
    }

    private Node addFirstRec (int initialCapacity, int weight, Node node){
        if (isEmpty(node) || maxRemainingCapacity(node)< weight){
            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            node =  addNewBinRec(b, node);
        }else if (maxRemainingCapacity(node.left) >= weight){
            node.left =  addFirstRec(initialCapacity, weight, node.left);
        }else if (node.bin.remainingCapacity() >= weight){
            node.bin.addObject(weight);
        }else{
            node.right =  addFirstRec(initialCapacity, weight, node.right);
        }
        return node;
    }

    public void addAll(int initialCapacity, int[] weights) {
        for (Integer i : weights){
            addFirst(initialCapacity, i);
        }
    }

    public List<Bin> toList() {
        return toListRec(root);
    }

    private List<Bin> toListRec (Node node){
        if (node == null){
            return new ArrayList<>();
        }else{
            return unionList(toListRec(node.left), node.bin, toListRec(node.right));
        }
    }

    private List<Bin> unionList (List<Bin> u1, Bin bin, List<Bin> u2){
        List<Bin> list = new ArrayList<>();
        for (Bin b : u1){
            list.append(b);
        }
        list.append(bin);
        for (Bin b : u2){
            list.append(b);
        }
        return list;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}