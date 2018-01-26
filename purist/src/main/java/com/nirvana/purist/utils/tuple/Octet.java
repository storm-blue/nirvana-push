package com.nirvana.purist.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Octet<E1, E2, E3, E4, E5, E6, E7, E8> extends Septet<E1, E2, E3, E4, E5, E6, E7> {

    private E8 e8;

    public Octet(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5, E6 e6, E7 e7, E8 e8) {
        super(e1, e2, e3, e4, e5, e6, e7);
        this.e8 = e8;
    }

    public E8 getE8() {
        return e8;
    }

    public void setE8(E8 e8) {
        this.e8 = e8;
    }
}
