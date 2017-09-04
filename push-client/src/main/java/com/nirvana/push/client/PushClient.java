package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PushClient {

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
            Channel ch = b.connect(host, port).sync().channel();

            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
            /*
             * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
             * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
             * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
             * */

                UTF8StringPayloadPart payload = new UTF8StringPayloadPart(line + "\r\n");
                BasePackage basePackage = new BasePackage(PackageType.PUBLISH, PackageLevel.NO_CONFIRM, false, false, null, payload);
                ch.writeAndFlush(basePackage.getByteBuf());
            }


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

}
