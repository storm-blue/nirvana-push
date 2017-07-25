package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

public interface Outputable {

    void output(ChannelHandlerContext context);

}
