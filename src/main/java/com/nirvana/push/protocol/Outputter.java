package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 输出器接口。
 */
public interface Outputter {

    /**
     * 输出到ChannelHandlerContext。
     */
    void output(ChannelHandlerContext context);

    /**
     * 输出到OutputStream。
     */
    void output(OutputStream outputStream) throws IOException;

}
