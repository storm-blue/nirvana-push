package com.nirvana.ezalor.utils;

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

    /**
     * 此方法截取给定4字节的目标bit位。
     *
     * @param x   目标int==byte[4]
     * @param bi1 开始bit位，从低位到高位，从1开始
     * @param bi2 结束bit位，最大32
     */
    public static int r(int x, int bi1, int bi2) {
        if (bi1 < 1 || bi2 > 32 || bi1 > bi2) {
            throw new IllegalArgumentException("bit位参数错误");
        }
        return (x >>> (bi1 - 1)) & rule[bi2 - bi1];
    }
}
