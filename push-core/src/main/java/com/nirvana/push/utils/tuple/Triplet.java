package com.nirvana.push.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Triplet<E1, E2, E3> extends Pair<E1, E2> {

    private E3 e3;

    public Triplet(E1 e1, E2 e2, E3 e3) {
        super(e1, e2);
        this.e3 = e3;
    }

    public E3 getE3() {
        return e3;
    }

    public void setE3(E3 e3) {
        this.e3 = e3;
    }
}
