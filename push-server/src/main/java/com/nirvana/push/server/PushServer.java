package com.nirvana.push.server;

import com.nirvana.push.protocol.decoder.PackageFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/8/1.
 */
public class PushServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushServer.class);

    private int port = 32222;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;
    //业务线程，因为处理业务，有可能使导致当前channel所在线程阻塞
    private EventLoopGroup busyGroup;

    PushServer() {
    }

    public PushServer(int port) {
        this.port = port;
    }


    ChannelFuture startServer() {

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
                // 解码器
                pipeline.addLast("framer", new PackageFrameDecoder(8192));
                // 字符串解码 和 编码
                pipeline.addLast("encoder", new StringEncoder());
                // 自己的逻辑Handler
                pipeline.addLast(busyGroup, "handler", new PushServerHandler());
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


    void destroy() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        busyGroup.shutdownGracefully();
    }

}
