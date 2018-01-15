package com.nirvana.xin.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Decade<E1, E2, E3, E4, E5, E6, E7, E8, E9, E10> extends Ennead<E1, E2, E3, E4, E5, E6, E7, E8, E9> {

    private E10 e10;

    public Decade(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5, E6 e6, E7 e7, E8 e8, E9 e9, E10 e10) {
        super(e1, e2, e3, e4, e5, e6, e7, e8, e9);
        this.e10 = e10;
    }

    public E10 getE10() {
        return e10;
    }

    public void setE10(E10 e10) {
        this.e10 = e10;
    }
}
