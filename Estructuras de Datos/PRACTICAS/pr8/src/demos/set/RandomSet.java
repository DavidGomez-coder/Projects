/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Creates a random set.
 */

package demos.set;

import dataStructures.set.Set;

import java.util.Random;

public class RandomSet {
    public static void fill(Set<Integer> set, int seed, int length) {
        Random rnd = new Random(seed);
        for (int i = 0; i < length; i++) {
            int r = rnd.nextInt(4);
            switch (r) {
                case 0:
                case 1:
                    int x = rnd.nextInt();
                    set.insert(x);
                    break;
                case 2:
                    int y = rnd.nextInt();
                    set.delete(y);
                    break;
                case 3:
                    int z = rnd.nextInt();
                    set.isElem(z);
            }
        }
    }
}
