package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 输出器接口。
 */
public interface Outputter {

    void output(ChannelHandlerContext context);

    void output(OutputStream outputStream) throws IOException;

}
