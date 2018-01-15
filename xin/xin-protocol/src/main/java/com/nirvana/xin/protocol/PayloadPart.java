package com.nirvana.xin.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Base包的实际负载传输字节。
 * Created by Nirvana on 2017/8/9.
 */
public class PayloadPart extends AbstractOutputable {

    protected PayloadPart() {
    }

    public PayloadPart(Outputable outputable) {
        byteBuf = outputable.getByteBuf();
    }

    public PayloadPart(ByteBuf buf) {
        this.byteBuf = buf;
    }

    public PayloadPart(byte[] bytes) {
        byteBuf = Unpooled.wrappedBuffer(bytes);
    }

    public PayloadPart(byte[] bytes, int offset, int length) {
        byteBuf = Unpooled.wrappedBuffer(bytes, offset, length);
    }

    @Override
    public String toString() {
        return "PayloadPart{" +
                "buf=" + byteBuf.toString(Charset.defaultCharset()) +
                '}';
    }
}
