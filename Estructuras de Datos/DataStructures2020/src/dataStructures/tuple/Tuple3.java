package dataStructures.tuple;

public class Tuple3<A, B, C> {
    private A a;
    private B b;
    private C c;

    public Tuple3(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A get_1() {
        return a;
    }

    public B get_2() {
        return b;
    }

    public C get_3() {
        return c;
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + a + "," + b + "," + c + ")";
    }
}
