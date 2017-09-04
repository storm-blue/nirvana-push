package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Base包的实际负载传输字节。
 * Created by Nirvana on 2017/8/9.
 */
public class PayloadPart extends AbstractOutputable {

    protected ByteBuf buf;

    protected PayloadPart() {
    }

    public PayloadPart(Outputable outputable) {
        buf = outputable.getByteBuf();
    }

    public PayloadPart(ByteBuf buf) {
        this.buf = buf;
    }

    public PayloadPart(byte[] bytes) {
        buf = Unpooled.wrappedBuffer(bytes);
    }

    public PayloadPart(byte[] bytes, int offset, int length) {
        buf = Unpooled.wrappedBuffer(bytes, offset, length);
    }

    @Override
    public ByteBuf getByteBuf() {
        return buf;
    }

    @Override
    public String toString() {
        return "PayloadPart{" +
                "buf=" + buf.toString(Charset.defaultCharset()) +
                '}';
    }
}
