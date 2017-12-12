package com.nirvana.ezalor.client.oio;

import com.nirvana.ezalor.client.PushClientInitializer;
import com.nirvana.ezalor.core.message.DefaultCardBox;
import com.nirvana.ezalor.core.message.MessageLevel;
import com.nirvana.ezalor.core.message.Package;
import com.nirvana.ezalor.core.message.PackageType;
import com.nirvana.ezalor.protocol.ProtocolPackage;
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

            Package pkg = new Package(PackageType.SUBSCRIBE, MessageLevel.NO_CONFIRM, null, false, new DefaultCardBox("default topic"));

            ProtocolPackage basePackage = ProtocolPackage.fromPackage(pkg);
            ch.writeAndFlush(basePackage.getByteBuf());

            ChannelFuture fc = ch.closeFuture().sync();


            fc.addListener(future -> System.out.println("客户端关闭"));

        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

}

