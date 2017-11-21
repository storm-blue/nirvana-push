package com.nirvana.push.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Pair<E1, E2> extends Unit<E1> {

    private E2 e2;

    public Pair(E1 e1, E2 e2) {
        super(e1);
        this.e2 = e2;
    }

    public E2 getE2() {
        return e2;
    }

    public void setE2(E2 e2) {
        this.e2 = e2;
    }
}
