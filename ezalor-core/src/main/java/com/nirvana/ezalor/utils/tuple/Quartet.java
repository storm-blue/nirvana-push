package com.nirvana.ezalor.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Quartet<E1, E2, E3, E4> extends Triplet<E1, E2, E3> {

    private E4 e4;

    public Quartet(E1 e1, E2 e2, E3 e3, E4 e4) {
        super(e1, e2, e3);
        this.e4 = e4;
    }

    public E4 getE4() {
        return e4;
    }

    public void setE4(E4 e4) {
        this.e4 = e4;
    }
}
