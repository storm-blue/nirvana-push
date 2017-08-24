package com.nirvana.push.server;

import com.nirvana.push.corex.ProtocolProcess;
import com.nirvana.push.corex.session.Client;
import com.nirvana.push.corex.session.NioSocketChannelSession;
import com.nirvana.push.corex.session.Session;
import com.nirvana.push.protocol.BasePackage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

@ChannelHandler.Sharable
public class PushServerHandlerX extends SimpleChannelInboundHandler<BasePackage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePackage msg) throws Exception {

        ProtocolProcess process = ProtocolProcess.getInstance();

        ctx.writeAndFlush("收到消息！！！");

        Session session = new NioSocketChannelSession(ctx.channel());

        switch (msg.getPackageType()) {
            case CONNECT:
                process.onConnect(session, msg);
                break;
            case SUBSCRIBE:
                process.onSubscribe(session, msg);
                break;
            case PUSH_MESSAGE_ACK:
                process.onPushMessageAck(session, msg);
                break;
            case EXACTLY_ONCE_MESSAGE_ACK:
                process.onExactlyOnceMessageAck(session, msg);
                break;
            case UNSUBSCRIBE:
                process.onUnsubscribe(session, msg);
                break;
            case PUBLISH:
                process.onPublish(session, msg);
                break;
            case PING:
                process.onPing(session, msg);
                break;
            case DISCONNECT:
                process.onDisconnect(session, msg);
                break;
        }
    }


    /**
     * 事件追踪，处理超时事件
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {

            Session session = new NioSocketChannelSession(ctx.channel());
            System.out.println("有连接断开");
            ProtocolProcess.getInstance().disConnect(session);

        }
    }

    /**
     * 断线移除会话
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = new NioSocketChannelSession(ctx.channel());
        System.out.println("有连接断开");
        ProtocolProcess.getInstance().disConnect(session);


    }


}
