package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 只是简单实现了输出接口。
 */
public abstract class AbstactOutputable implements Outputable, Outputter {

    @Override
    public void output(ChannelHandlerContext context) {
        context.write(getByteBuf());
    }

    @Override
    public void output(OutputStream outputStream) throws IOException {
        ByteBuf buf = getByteBuf();
        byte[] bytes = new byte[getByteBuf().readableBytes()];
        buf.readBytes(bytes);
        outputStream.write(bytes);
        outputStream.flush();
    }

    public int getSize() {
        return getByteBuf().readableBytes();
    }
}
