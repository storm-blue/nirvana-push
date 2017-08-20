package com.nirvana.push.server;

import io.netty.channel.ChannelFuture;

/**
 * 启动函数，主线程所在
 */
public class StartServer {

    /**
     * 启动服务
     * @param args
     */
    public static void main(String[] args){
        final PushServer pushServer = new PushServer();
        ChannelFuture future = pushServer.startServer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> pushServer.destory()));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
