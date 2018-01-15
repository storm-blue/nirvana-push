package com.nirvana.xin.client;

import com.nirvana.xin.protocol.decoder.PackageFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class PushClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

       pipeline.addLast("decoder", new PackageFrameDecoder(8192));

        pipeline.addLast("handler", new PushClientHandler());
    }
}
