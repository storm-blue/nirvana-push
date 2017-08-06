package com.nirvana.push.utils;

/**
 * bit量尺。
 * Created by Nirvana on 2017/8/1.
 */
public class BitRuler {

    private static final int[] rule = new int[32];

    static {
        rule[0] = 1;
        for (int i = 1; i < 32; i++) {
            rule[i] = (rule[i - 1] + 1) * 2 - 1;
        }
    }

    public static int r(int x, int bi1, int bi2) {
        return (x >>> (bi1 - 1)) & rule[bi2 - bi1];
    }
}
