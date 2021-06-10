package demos.set;

import dataStructures.set.*;

public class SetsPerformance {
    public enum Implementation { SortedLinked, BST, AVL, Hash }

    public static double test(Implementation impl, int seed, int length) {
        long t0 = System.currentTimeMillis();
        Set<Integer> set = impl==Implementation.SortedLinked ? new SortedLinkedSet<>()
                         : impl==Implementation.BST          ? new BSTSet<>()
                         : impl==Implementation.AVL          ? new AVLSet<>()
                                                             : new HashSet<>();
        RandomSet.fill(set, seed, length);
        long t1 = System.currentTimeMillis();
        return (t1-t0) / 1e3; //execution time in seconds
    }

    static double avgTime(Implementation impl, int tests) {
        double t = 0.0;

        for(int s=0; s<tests; s++)
            t += test(impl, s, 50000);

        return t/tests;
    }

    public static void main(String[] args) {
        final int tests = 10;

        double t1 = avgTime(Implementation.SortedLinked, tests);
        double t2 = avgTime(Implementation.BST, tests);
        double t3 = avgTime(Implementation.AVL, tests);
        double t4 = avgTime(Implementation.Hash, tests);

        System.out.printf("Average execution time for SortedLinkedSet is %f seconds\n", t1);
        System.out.printf("Average execution time for BSTSet is %f seconds\n", t2);
        System.out.printf("Average execution time for AVLSet is %f seconds\n", t3);
        System.out.printf("Average execution time for HashSet is %f seconds\n", t4);
        System.out.printf("BSTSet is %.2f times faster\n", t1/t2);
        System.out.printf("AVLSet is %.2f times faster\n", t1/t3);
        System.out.printf("HashSet is %.2f times faster\n", t1/t4);
        System.out.printf("BSTSet is %.2f times faster than AVLSet\n", t3/t2);

        System.out.printf("HashSet is %.2f times faster than BSTSet\n", t2/t4);
        System.out.printf("HashSet is %.2f times faster than AVLSet\n", t3/t4);
    }
}
