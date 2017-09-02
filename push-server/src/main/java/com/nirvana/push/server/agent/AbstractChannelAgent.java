package com.nirvana.push.server.agent;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

/**
 * 代理类的骨架实现。
 * Created by Nirvana on 2017/8/7.
 */
public abstract class AbstractChannelAgent implements Agent {

    private SocketChannel channel;

    public AbstractChannelAgent(SocketChannel channel) {
        this.channel = channel;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    @Override
    public void onCommand(BasePackage pkg) {

        boolean identifiable = pkg.isIdentifiable();
        Long identifier = pkg.getIdentifier();
        ByteBuf payload = pkg.getPayload() == null ? null : pkg.getPayload().getByteBuf();

        switch (pkg.getPackageType()) {
            case CONNECT:
                onConnect(identifiable, identifier, payload);
                break;
            case SUBSCRIBE:
                onSubscribe(identifiable, identifier, payload);
                break;
            case PUSH_MESSAGE_ACK:
                onPushMessageAck(identifiable, identifier, payload);
                break;
            case EXACTLY_ONCE_MESSAGE_ACK:
                onExactlyOnceMessageAck(identifiable, identifier, payload);
                break;
            case UNSUBSCRIBE:
                onUnsubscribe(identifiable, identifier, payload);
                break;
            case PUBLISH:
                onPublish(pkg.getPackageLevel(), pkg.isRetain(), identifiable, identifier, payload);
                break;
            case PING:
                onPing(identifiable, identifier, payload);
                break;
            case DISCONNECT:
                onDisconnect(identifiable, identifier, payload);
                break;
        }

    }

    /**
     * 像远程发送一个协议包。
     */
    public void sendPackage(BasePackage pkg) {
        channel.write(pkg.getByteBuf());
        channel.flush();
    }

    /**
     * 客户端连接请求。
     */
    abstract void onConnect(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 订阅请求。
     */
    abstract void onSubscribe(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 接收到推送消息确认。
     */
    abstract void onPushMessageAck(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 接收到有且仅一次推送消息确认。
     */
    abstract void onExactlyOnceMessageAck(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 取消订阅请求。
     */
    abstract void onUnsubscribe(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 发布消息请求。
     */
    abstract void onPublish(PackageLevel level, boolean retain, boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 客户端心跳。
     */
    abstract void onPing(boolean identifiable, Long identifier, ByteBuf data);

    /**
     * 客户端断开连接请求。
     */
    abstract void onDisconnect(boolean identifiable, Long identifier, ByteBuf data);
}
