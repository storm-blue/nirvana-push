package com.nirvana.push.client;

import com.nirvana.push.core.message.DefaultCardBox;
import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.message.Package;
import com.nirvana.push.core.message.PackageType;
import com.nirvana.push.protocol.ProtocolPackage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PushClient {

    private static int INIT_CONNECTIONS = 1000;

    static Set<Channel> channels = new ConcurrentHashMap<Channel, Boolean>().keySet(true);

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
                channels.add(ch);
                connect(ch, "channel" + i);
                subscribe(ch, "default");
                if (INIT_CONNECTIONS > 20) {
                    if ((i + 1) % (INIT_CONNECTIONS / 20) == 0) {
                        System.out.println("初始化完成：" + (i + 1) * 100 / INIT_CONNECTIONS + "%");
                    }
                }
            }

            System.out.println("连接数量：" + channels.size());

            for (int i = 1; ; ) {
                for (Channel channel : channels) {
                    System.out.println("客户端" + channel.id() + "开始发送包。期望推送数：" + channels.size() * i);
                    publish(channel, "default", "用户" + channel.id() + " : 大家好，现在时间戳是" + System.currentTimeMillis());
                    Thread.sleep(200);
                    i++;
                }
            }


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

    private static ProtocolPackage getPackage(PackageType type, String[] values) {
        Package pkg = new Package(type, MessageLevel.NO_CONFIRM, null, false, new DefaultCardBox((Object[]) values));
        return ProtocolPackage.fromPackage(pkg);
    }

    private static void connect(Channel channel, String username) {
        channel.writeAndFlush(getPackage(PackageType.CONNECT, new String[]{username, "123456"}).getByteBuf());
    }

    private static void subscribe(Channel channel, String topicName) {
        channel.writeAndFlush(getPackage(PackageType.SUBSCRIBE, new String[]{topicName}).getByteBuf());
    }

    private static void publish(Channel channel, String topicName, String message) {
        channel.writeAndFlush(getPackage(PackageType.PUBLISH, new String[]{topicName, message, MessageLevel.NO_CONFIRM.name()}).getByteBuf());
    }

}
