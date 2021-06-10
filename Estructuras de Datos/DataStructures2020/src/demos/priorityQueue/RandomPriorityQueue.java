 /**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Perform random operations on a queue
 */

package demos.priorityQueue;

 import dataStructures.priorityQueue.PriorityQueue;
 import dataStructures.queue.Queue;

 import java.util.Random;

 public class RandomPriorityQueue {
     public static void fill(PriorityQueue<Integer> q, int seed, int length) {
         Random rnd = new Random(seed);
         for (int i = 0; i < length; i++) {
             int r = rnd.nextInt(3);
             switch (r) {
             case 0:
             case 1:
                 q.enqueue(rnd.nextInt(1000));
                 break;
             case 2:
                 if (!q.isEmpty())
                     q.dequeue();
             }
         }
     }
 }
