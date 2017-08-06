package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 包尾。
 */
public class Footer extends AbstactOutputable {

    private static final Footer footer = new Footer();

    public static final int FOOT_SIZE = 2;

    private ByteBuf buf;

    private Footer() {
        buf = Unpooled.wrappedBuffer(new byte[]{(byte) 0xff, (byte) 0xff});
    }

    public static Footer getFooter() {
        return footer;
    }

    public static boolean checkFooter(ByteBuf buf) {
        return buf.readableBytes() >= 2 && buf.getByte(0) == (byte) 0xff && buf.getByte(1) == (byte) 0xff;
    }

    public static boolean checkFooter(byte[] bytes) {
        return bytes.length >= 2 && bytes[0] == (byte) 0xff & bytes[1] == (byte) 0xff;
    }

    @Override
    public ByteBuf getByteBuf() {
        return buf;
    }

    @Override
    public String toString() {
        return "Footer{" +
                "buf=" + buf +
                '}';
    }
}
