package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

/**
 * 输出器接口。
 */
public interface Outputter {

    void output(ChannelHandlerContext context);

}
