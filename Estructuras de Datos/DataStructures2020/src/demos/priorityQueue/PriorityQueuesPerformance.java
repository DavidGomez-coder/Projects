/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Experimental comparison for queue implementations
 */

package demos.priorityQueue;

import dataStructures.priorityQueue.*;

public class PriorityQueuesPerformance {
	
    public enum Implementation { BinaryHeapPriorityQueue, BinaryHeapPriorityQueueFIFO, 
        WBLeftistHeapPriorityQueue, WBLeftistHeapPriorityQueueFIFO,
		MaxiphobicPriorityQueue, MaxiphobicPriorityQueueFIFO, LinkedPriorityQueue}
	
	public static double test(Implementation impl, int seed, int length) {
		long t0 = System.currentTimeMillis();
        PriorityQueue<Integer> queue =
            impl==Implementation.BinaryHeapPriorityQueue ? new BinaryHeapPriorityQueue<>()
			: impl == Implementation.BinaryHeapPriorityQueueFIFO ? new BinaryHeapPriorityQueueFIFO<>()
			: impl == Implementation.WBLeftistHeapPriorityQueue ?new WBLeftistHeapPriorityQueue<>()
			: impl == Implementation.WBLeftistHeapPriorityQueueFIFO ?new WBLeftistHeapPriorityQueueFIFO<>()
			: impl == Implementation.MaxiphobicPriorityQueue ?new MaxiphobicHeapPriorityQueue<>()
			: impl == Implementation.MaxiphobicPriorityQueueFIFO ?new MaxiphobicHeapPriorityQueueFIFO<>()
			: new LinkedPriorityQueue<>();
		RandomPriorityQueue.fill(queue, seed, length);
		long t1 = System.currentTimeMillis();
		return (t1-t0) / 1e3; //execution time in seconds
	}	
	
	static double avgTime(Implementation impl, int tests) {
		double t = 0.0;
		
		for(int s=0; s<tests; s++) 
			t += test(impl, s, 100000);

		return t/tests;	
	}
	
	public static void main(String[] args) {
		
		final int tests = 10;
		
		double t1 = avgTime(Implementation.BinaryHeapPriorityQueue, tests);
		System.out.printf("Average execution time for BinaryHeapPriorityQueue is %f seconds\n", t1);
		double t2 = avgTime(Implementation.BinaryHeapPriorityQueueFIFO, tests);
		System.out.printf("Average execution time for BinaryHeapPriorityQueueFIFO is %f seconds\n", t2);
		double t3 = avgTime(Implementation.WBLeftistHeapPriorityQueue, tests);
		System.out.printf("Average execution time for WBLeftistHeapPriorityQueue is %f seconds\n", t3);
		double t4 = avgTime(Implementation.WBLeftistHeapPriorityQueueFIFO, tests);
		System.out.printf("Average execution time for WBLeftistHeapPriorityQueueFIFO is %f seconds\n", t4);
		double t5 = avgTime(Implementation.MaxiphobicPriorityQueue, tests);
		System.out.printf("Average execution time for MaxiphobicPriorityQueue is %f seconds\n", t5);
		double t6 = avgTime(Implementation.MaxiphobicPriorityQueueFIFO, tests);
		System.out.printf("Average execution time for MaxiphobicPriorityQueueFIFO is %f seconds\n", t6);
		double t7 = avgTime(Implementation.LinkedPriorityQueue, tests);
		System.out.printf("Average execution time for LinkedPriorityQueue is %f seconds\n", t7);
	}
}