package com.nirvana.push.protocol;

/**
 * 包尾。
 */
public class Footer implements Byteable {

    private static final Footer footer = new Footer();

    private Footer() {
    }

    public static Footer getFooter() {
        return footer;
    }

    private static final byte[] bytes = new byte[]{(byte) 0xff, (byte) 0xff};

    public byte[] getBytes() {
        return bytes;
    }
}
