package com.nirvana.xin.codec.encoder;

/**
 * Created by Nirvana on 2018/1/15.
 */
public class ScalableNumberEncoder implements Encoder<Long> {

    private static final ScalableNumberEncoder INSTANCE = new ScalableNumberEncoder();

    private ScalableNumberEncoder() {}

    public static ScalableNumberEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] encode(Long number) {
        byte[] bytes = new byte[8];
        int i = 0;
        while (number > 0) {
            if (i > bytes.length - 1) {
                throw new IllegalArgumentException("number out of range.");
            }
            bytes[i] = (byte) (number % 128);
            number = number / 128;
            if (number > 0) {
                bytes[i] = (byte) (bytes[i] | (byte) 128);
            }
            i++;
        }
        byte[] output = new byte[i];
        System.arraycopy(bytes, 0, output, 0, i);
        return output;
    }

}
