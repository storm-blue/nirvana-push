package com.nirvana.ezalor.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 只是简单实现了输出接口。
 * Created by Nirvana on 2017/8/2.
 */
public abstract class AbstractOutputable implements Outputable, Outputter, Releasable {

    protected ByteBuf byteBuf;

    protected boolean released = false;

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

    /**
     * 使用完之后需要手动释放。否则有可能会造成内存泄漏。
     */
    @Override
    public void release() {
        if (byteBuf != null && !released) {
            byteBuf.release();
            released = true;
        }
    }

    @Override
    public ByteBuf getByteBuf() {
        if (released) {
            throw new IllegalStateException("此对象已经释放，无法获取");
        }
        return byteBuf;
    }
}
