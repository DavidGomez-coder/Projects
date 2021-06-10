package demos.set;

import dataStructures.set.LinkedSet;
import dataStructures.set.Set;
import dataStructures.set.SortedArraySet;

public class Main {
    public static void main(String[] args) {
        Set<String> set = new LinkedSet<>();
        set.insert("hola");
        set.insert("como");
        set.insert("tras");
        set.insert("dado");
        System.out.println(set);
        set.delete("que");
        System.out.println(set);
        for (String s: set) {
            System.out.println(s);
        }
    }
}
