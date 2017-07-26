package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

/**
 * 只是简单实现了输出接口。
 */
public abstract class AbstactByteable implements Byteable, Outputter {

    @Override
    public void output(ChannelHandlerContext context) {
        context.channel().write(getBytes());
    }
}
