package com.nirvana.purist.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Ennead<E1, E2, E3, E4, E5, E6, E7, E8, E9> extends Octet<E1, E2, E3, E4, E5, E6, E7, E8> {

    private E9 e9;

    public Ennead(E1 e1, E2 e2, E3 e3, E4 e4, E5 e5, E6 e6, E7 e7, E8 e8, E9 e9) {
        super(e1, e2, e3, e4, e5, e6, e7, e8);
        this.e9 = e9;
    }

    public E9 getE9() {
        return e9;
    }

    public void setE9(E9 e9) {
        this.e9 = e9;
    }
}
