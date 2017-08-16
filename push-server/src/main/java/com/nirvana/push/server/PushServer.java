package com.nirvana.push.server;

import com.nirvana.push.core.broker.AbstractMessageBroker;
import com.nirvana.push.core.broker.AutoMessageBroker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by Nirvana on 2017/8/1.
 */
public class PushServer {

    public static final AbstractMessageBroker PUBLIC_MESSAGE_BROKER = new AutoMessageBroker();

    private static final int port = 32222;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(AgentNioServerSocketChannel.class);
            b.childHandler(new PushServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
