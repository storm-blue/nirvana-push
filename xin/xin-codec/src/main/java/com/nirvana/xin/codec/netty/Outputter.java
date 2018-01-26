package com.nirvana.xin.codec.netty;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 输出器接口。
 * Created by Nirvana on 2017/8/2.
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
