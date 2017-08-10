package com.nirvana.push.protocolv1;

import com.nirvana.push.protocol.AbstractOutputable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 包尾。
 * Created by Nirvana on 2017/8/2.
 */
public class Footer extends AbstractOutputable {

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
