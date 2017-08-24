package com.nirvana.push.server;

import com.nirvana.push.core.broker.AbstractMessageBroker;
import com.nirvana.push.core.broker.AutoMessageBroker;
import com.nirvana.push.protocol.decoder.PackageFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/8/1.
 */
public class PushServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushServer.class);

    public static final AbstractMessageBroker PUBLIC_MESSAGE_BROKER = new AutoMessageBroker();

    private int port = 32222;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;
    //业务线程，因为处理业务，有可能使导致当前channel所在线程阻塞
    private EventLoopGroup busyGroup;

    //读超时时间
    private int readerIdleTimeSeconds;
    //写超时时间
    private int writerIdleTimeSeconds;
    //所有类型的超时时间
    private int allIdleTimeSeconds;

    public ChannelFuture startServer() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        busyGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(AgentNioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 心跳
                pipeline.addLast("heart", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
                // 解码器
                pipeline.addLast("framer", new PackageFrameDecoder());
                // 字符串解码 和 编码
                pipeline.addLast("encoder", new StringEncoder());
                // 自己的逻辑Handler
                pipeline.addLast(busyGroup, "handler", new PushServerHandlerX());
            }
        });

        try {
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            channel = f.channel();
            LOGGER.info("服务器已启动，端口:" + port);
            return f;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void destory() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        busyGroup.shutdownGracefully();
    }


    public PushServer(int port) {
        this(port, 60000, 60000, 120000);
    }


    public PushServer(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this(32222, readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    public PushServer(int port, int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this.port = port;
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
        this.allIdleTimeSeconds = allIdleTimeSeconds;
    }

    public PushServer() {
        this(32222);
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
