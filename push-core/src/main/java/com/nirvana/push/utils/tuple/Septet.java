package com.nirvana.push.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Septet<E1, E2, E3, E4, E5, E6, E7> extends Sextet<E1, E2, E3, E4, E5, E6> {

    private E7 e7;

    public Septet(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5, E6 e6, E7 e7) {
        super(e1, e2, e3, e4, e5, e6);
        this.e7 = e7;
    }

    public E7 getE7() {
        return e7;
    }

    public void setE7(E7 e7) {
        this.e7 = e7;
    }
}
