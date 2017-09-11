package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.l2.DSTPackage;
import com.nirvana.push.protocol.l2.DSTPayloadPart;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PushClient {

    private static int INIT_CONNECTIONS = 10000;

    static Map<ChannelId, Channel> channels = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PushClientInitializer());

            for (int i = 0; i < INIT_CONNECTIONS; i++) {
                String host = "127.0.0.1";
                int port = 32222;
                Channel ch = b.connect(host, port).sync().channel();
                ch.id();
                channels.put(ch.id(), ch);
                subscribe(ch, "default topic");
                if ((i + 1) % (INIT_CONNECTIONS / 20) == 0) {
                    System.out.println("初始化完成：" + (i + 1) * 100 / INIT_CONNECTIONS + "%");
                }
            }

            System.out.println("连接数量：" + channels.size());

            for (int i = 1; ; i++) {
                for (ChannelId id : channels.keySet()) {
                    System.out.println("客户端" + id + "开始发送包。期望推送数：" + channels.size() * i);
                    publish(channels.get(id), "default topic", "用户" + id + " : " + System.currentTimeMillis());
                    Thread.sleep(200);
                }
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
