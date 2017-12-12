package com.nirvana.ezalor.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Quintet<E1, E2, E3, E4, E5> extends Quartet<E1, E2, E3, E4> {

    private E5 e5;

    public Quintet(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5) {
        super(e1, e2, e3, e4);
        this.e5 = e5;
    }

    public E5 getE5() {
        return e5;
    }

    public void setE5(E5 e5) {
        this.e5 = e5;
    }
}
