package com.nirvana.push.core.agent;

import com.nirvana.push.core.AbstractDestroyable;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import io.netty.buffer.ByteBuf;

/**
 * 代理类的骨架实现。
 * Created by Nirvana on 2017/8/7.
 */
public abstract class AbstractAgent extends AbstractDestroyable implements Agent {

    @Override
    public final void onCommand(BasePackage pkg) {

        Long identifier = pkg.getIdentifier();
        ByteBuf payload = pkg.getPayload() == null ? null : pkg.getPayload().getByteBuf();

        switch (pkg.getPackageType()) {
            case CONNECT:
                onConnect(identifier, payload);
                break;
            case SUBSCRIBE:
                onSubscribe(identifier, payload);
                break;
            case PUSH_MESSAGE_ACK:
                onPushMessageAck(identifier, payload);
                break;
            case EXACTLY_ONCE_MESSAGE_ACK:
                onExactlyOnceMessageAck(identifier, payload);
                break;
            case UNSUBSCRIBE:
                onUnsubscribe(identifier, payload);
                break;
            case PUBLISH:
                onPublish(pkg.getPackageLevel(), pkg.isRetain(), identifier, payload);
                break;
            case PING:
                onPing(identifier, payload);
                break;
            case DISCONNECT:
                onDisconnect(identifier, payload);
                break;
        }

    }

    /**
     * 客户端连接请求。
     */
    protected abstract void onConnect(Long identifier, ByteBuf data);

    /**
     * 订阅请求。
     */
    protected abstract void onSubscribe(Long identifier, ByteBuf data);

    /**
     * 接收到推送消息确认。
     */
    protected abstract void onPushMessageAck(Long identifier, ByteBuf data);

    /**
     * 接收到有且仅一次推送消息确认。
     */
    protected abstract void onExactlyOnceMessageAck(Long identifier, ByteBuf data);

    /**
     * 取消订阅请求。
     */
    protected abstract void onUnsubscribe(Long identifier, ByteBuf data);

    /**
     * 发布消息请求。
     */
    protected abstract void onPublish(PackageLevel level, boolean retain, Long identifier, ByteBuf data);

    /**
     * 客户端心跳。
     */
    protected abstract void onPing(Long identifier, ByteBuf data);

    /**
     * 客户端断开连接请求。
     */
    protected abstract void onDisconnect(Long identifier, ByteBuf data);
}
