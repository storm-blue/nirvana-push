package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * 字符串负载。
 * Created by Nirvana on 2017/8/11.
 */
public class UTF8StringPayloadPart extends PayloadPart {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private String message;

    protected UTF8StringPayloadPart() {
    }

    public UTF8StringPayloadPart(String message) {
        this.message = message;
        this.buf = Unpooled.copiedBuffer(message, CHARSET);
    }

    public UTF8StringPayloadPart(ByteBuf buf) {
        this.message = buf.toString(CHARSET);
        this.buf = buf;
    }

    public String getMessage() {
        return message;
    }
}
