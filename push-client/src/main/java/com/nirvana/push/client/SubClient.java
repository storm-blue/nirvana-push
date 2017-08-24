package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SubClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PushClientInitializer());

            // 连接服务端
            String host = "127.0.0.1";
            int port = 32222;
            ChannelFuture f = b.connect(host, port).sync();


            Channel ch = f.channel();


            UTF8StringPayloadPart payload = new UTF8StringPayloadPart("订阅" + "\r\n");
            BasePackage basePackage = new BasePackage(PackageType.SUBSCRIBE, PackageLevel.NO_CONFIRM, false, false, null, payload);
            ch.writeAndFlush(basePackage.getByteBuf());

            ChannelFuture fc = ch.closeFuture().sync();


            fc.addListener(future -> System.out.println("客户端关闭"));

        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

}

