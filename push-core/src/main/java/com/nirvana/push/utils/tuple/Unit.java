package com.nirvana.push.utils.tuple;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class Unit<E1> {

    private E1 e1;

    public Unit(E1 e1) {
        this.e1 = e1;
    }

    public E1 getE1() {
        return e1;
    }

    public void setE1(E1 e1) {
        this.e1 = e1;
    }
}
