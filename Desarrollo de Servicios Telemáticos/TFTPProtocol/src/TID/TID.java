package TID;

/**
 * David Gómez Pérez
 */
public class TID<A, B> {

    private A a;
    private B b;

    public TID(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A _1() {
        return this.a;
    }

    public B _2() {
        return this.b;
    }


    public boolean equals(TID<A, B> obj) {
        return obj._1().equals(this._1()) && obj._2().equals(this._2());
    }

    @Override
    public String toString() {
        return "[" + this._1() + "," + this._2() + "]";
    }
}
