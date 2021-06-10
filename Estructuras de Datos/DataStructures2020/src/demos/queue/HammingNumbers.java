/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Hamming numbers as an Iterable
 */

package demos.queue;

import java.util.Iterator;

import dataStructures.queue.ArrayQueue;
import dataStructures.queue.Queue;

public class HammingNumbers implements Iterable<Integer> {
	@Override
	public Iterator<Integer> iterator() {
		return new HammingIter();
	}

	private class HammingIter implements Iterator<Integer> {
		Queue<Integer> q2, q3, q5;

		public HammingIter() {
			q2 = new ArrayQueue<>();
			q2.enqueue(1);
			q3 = new ArrayQueue<>();
			q3.enqueue(1);
			q5 = new ArrayQueue<>();
			q5.enqueue(1);
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Integer next() {
			Integer n2 = q2.first();
			Integer n3 = q3.first();
			Integer n5 = q5.first();

			Integer hamming = Math.min(n2, Math.min(n3, n5));

			if (hamming.equals(n2))
				q2.dequeue();
			if (hamming.equals(n3))
				q3.dequeue();
			if (hamming.equals(n5))
				q5.dequeue();

			q2.enqueue(hamming * 2);
			q3.enqueue(hamming * 3);
			q5.enqueue(hamming * 5);

			return hamming;
		}
	}
}
