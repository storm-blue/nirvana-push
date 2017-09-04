package com.nirvana.push.server;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.core.agent.Agent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/8/1.
 */
@ChannelHandler.Sharable
public class PushServerHandler extends SimpleChannelInboundHandler<BasePackage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePackage msg) throws Exception {
        LOGGER.info("开始处理接收的协议包：{}", msg);
        Agent agent = ((AgentNioSocketChannel) ctx.channel()).getAgent();
        agent.onCommand(msg);
    }


    /**
     * 事件追踪，处理超时事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //直接关闭channel
            ctx.channel().close();

        }
    }
}
