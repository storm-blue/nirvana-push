package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 只是简单实现了输出接口。
 */
public abstract class AbstactByteable implements Byteable, Outputter {

    private byte[] bytes;

    private int offset;

    private int length;

    @Override
    public void output(ChannelHandlerContext context) {
        context.channel().write(getBytes());
    }

    @Override
    public void output(OutputStream outputStream) throws IOException {
        outputStream.write(getBytes(), offset, length);
        outputStream.flush();
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    protected void setBytes(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
    }

    protected void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.offset = 0;
        this.length = bytes.length;
    }

    public int getSize() {
        return length;
    }
}
