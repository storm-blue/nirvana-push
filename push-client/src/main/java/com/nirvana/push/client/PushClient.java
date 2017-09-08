package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.p2.DSTPackage;
import com.nirvana.push.protocol.p2.DSTPayloadPart;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

public class PushClient {

    private static int connections = 10000;

    private static List<Channel> channels = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PushClientInitializer());

            for (int i = 0; i < connections; i++) {
                String host = "127.0.0.1";
                int port = 32222;
                Channel ch = b.connect(host, port).sync().channel();
                channels.add(ch);
                subscribe(ch, "default topic");
            }

            System.out.println("连接数量：" + channels.size());

            while (true) {
                int random = (int) ((Math.random()) * (connections - 1));
                System.out.println("客户端" + random + "开始发送包。");
                publish(channels.get(random), "default topic", "用户" + random + " : " + System.currentTimeMillis());
                Thread.sleep(5000);
            }


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

    private static BasePackage getPackage(PackageType type, String[] values) {
        return new BasePackage(type, PackageLevel.NO_CONFIRM, false, null, new DSTPayloadPart(new DSTPackage(values)));
    }

    private static void subscribe(Channel channel, String topicName) {
        channel.writeAndFlush(getPackage(PackageType.SUBSCRIBE, new String[]{topicName}).getByteBuf());
    }

    private static void publish(Channel channel, String topicName, String message) {
        channel.writeAndFlush(getPackage(PackageType.PUBLISH, new String[]{topicName, message}).getByteBuf());
    }

}
