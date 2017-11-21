package com.nirvana.push.client.oio;

import com.nirvana.push.client.PushClientInitializer;
import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.message.PackageType;
import com.nirvana.push.protocol.ProtocolPackage;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import com.nirvana.push.protocol.l2.DSTPackage;
import com.nirvana.push.protocol.l2.DSTPayloadPart;
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


            UTF8StringPayloadPart payload = new DSTPayloadPart(new DSTPackage(new String[]{"default topic"}));
            ProtocolPackage basePackage = new ProtocolPackage(PackageType.SUBSCRIBE, MessageLevel.NO_CONFIRM, false, null, payload);
            ch.writeAndFlush(basePackage.getByteBuf());

            ChannelFuture fc = ch.closeFuture().sync();


            fc.addListener(future -> System.out.println("客户端关闭"));

        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

}

