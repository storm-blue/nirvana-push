package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * 包体。
 */
public class Body extends AbstactOutputable {

    private String content;

    private String charset;

    private ByteBuf buf;

    public Body(ByteBuf byteBuf, String charset) {
        content = byteBuf.toString(Charset.forName(charset));
        this.charset = charset;
        buf = byteBuf;
    }

    public Body(byte[] bytes, int index, int length, String charset) {
        this(Unpooled.wrappedBuffer(bytes, index, length), charset);
    }

    public Body(String content, String charset) {
        this.content = content;
        this.charset = charset;
        buf = Unpooled.copiedBuffer(content, Charset.forName(charset));
    }

    public String getContent() {
        return content;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public ByteBuf getByteBuf() {
        return buf;
    }

    @Override
    public String toString() {
        return "Body{" +
                "content='" + content + '\'' +
                ", charset='" + charset + '\'' +
                '}';
    }

}
