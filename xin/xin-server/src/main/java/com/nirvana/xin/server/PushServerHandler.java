package com.nirvana.xin.server;

import com.nirvana.xin.protocol.ProtocolPackage;
import com.nirvana.xin.core.agent.Agent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/8/1.
 */
@ChannelHandler.Sharable
public class PushServerHandler extends SimpleChannelInboundHandler<ProtocolPackage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolPackage msg) throws Exception {
        LOGGER.debug("Start process received package: {}", msg);
        Agent agent = ((AgentNioSocketChannel) ctx.channel()).getAgent();
        agent.onPackage(msg.getPackage());
        msg.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("Exception occurs, close connection: ", cause);
        ctx.close();
    }
}
