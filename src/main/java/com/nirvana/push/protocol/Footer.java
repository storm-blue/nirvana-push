package com.nirvana.push.protocol;

/**
 * 包尾。
 */
public class Footer extends AbstactByteable {

    private static final Footer footer = new Footer();

    public static final int FOOT_SIZE = 2;

    private Footer() {
        setBytes(new byte[]{(byte) 0xff, (byte) 0xff});
    }

    public static Footer getFooter() {
        return footer;
    }


}
