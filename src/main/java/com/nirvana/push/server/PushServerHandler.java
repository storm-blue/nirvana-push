package com.nirvana.push.server;

import com.nirvana.push.protocol.Package;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushServerHandler extends SimpleChannelInboundHandler<Package> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Package msg) throws Exception {
        LOGGER.info("开始处理接收的协议包：{}", msg);
    }
}
