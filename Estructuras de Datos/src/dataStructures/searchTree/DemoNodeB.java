package dataStructures.searchTree;


public class DemoNodeB {

    public static void main(String[] args) {

        NodeB<Character> ejemplo =
                new NodeB<>(new NodeB<>(new NodeB<>(new NodeB<>('s'), 't', new NodeB<>('q')), 'b', new NodeB<>(new NodeB<>('e'), 'f', (new NodeB<>('p')))),
                        'w',
                        new NodeB<>(new NodeB<>('r'), 'a', (new NodeB<>(new NodeB<>('k'), 'u', new NodeB<>('d')))));
        NodeB<Integer> ejemploI =
                new NodeB<>(new NodeB<>(new NodeB<>(new NodeB<>(3), 6, new NodeB<>(8)), 4, new NodeB<>(new NodeB<>(7), 5, (new NodeB<>(10)))),
                        6,
                        new NodeB<>(new NodeB<>(2), 5, (new NodeB<>(new NodeB<>(9), 4, new NodeB<>(7)))));
        System.out.println("ejemplo");
        System.out.println(ejemplo);
        System.out.println("ejemploI");
        System.out.println(ejemploI);

        System.out.println("suma(ejemploI)");
        System.out.println(NodeB.suma(ejemploI));
        System.out.println("prof(ejemplo)");
        System.out.println(NodeB.prof(ejemplo));
        System.out.println("anotaProf(ejemplo)");
        System.out.println(NodeB.anotaProf(ejemplo));
        System.out.println("anotaSumProf(ejemploI)");
        System.out.println(NodeB.anotaSumProf(ejemploI));
        System.out.println("atLevel(ejemplo, 2");
        System.out.println(NodeB.atLevel(2, ejemplo));
        System.out.println("preOrderB(ejemplo)");
        System.out.println(NodeB.preOrderB(ejemplo));
        System.out.println("inOrderB(ejemplo)");
        System.out.println(NodeB.inOrderB(ejemplo));
        System.out.println("postOrderB(ejemplo)");
        System.out.println(NodeB.postOrderB(ejemplo));
        System.out.println("pathsToB(ejemplo)");
        System.out.println((NodeB.pathsToB(4, ejemploI)));

    }
}
