package com.nirvana.push.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Sextet<E1, E2, E3, E4, E5, E6> extends Quintet<E1, E2, E3, E4, E5> {

    private E6 e6;

    public Sextet(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5, E6 e6) {
        super(e1, e2, e3, e4, e5);
        this.e6 = e6;
    }

    public E6 getE6() {
        return e6;
    }

    public void setE6(E6 e6) {
        this.e6 = e6;
    }
}
