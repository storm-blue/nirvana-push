package com.nirvana.push.server.agent;

import com.nirvana.push.core.agent.AbstractAgent;
import com.nirvana.push.core.publisher.NamePublisher;
import com.nirvana.push.core.publisher.DefaultNamePublisher;
import com.nirvana.push.core.subscriber.StringSubscriber;
import com.nirvana.push.core.subscriber.Subscriber;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

/**
 * 绑定
 * Created by Nirvana on 2017/8/13.
 */
public class DefaultAgent extends AbstractAgent {

    private SocketChannel channel;

    private Subscriber subscriber = new StringSubscriber(this);

    protected static final NamePublisher<String> publisher = new DefaultNamePublisher<>();

    public DefaultAgent(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onConnect(Long identifier, ByteBuf data) {

    }

    @Override
    public void onSubscribe(Long identifier, ByteBuf data) {
    }

    @Override
    public void onPushMessageAck(Long identifier, ByteBuf data) {

    }

    @Override
    public void onExactlyOnceMessageAck(Long identifier, ByteBuf data) {

    }

    @Override
    public void onUnsubscribe(Long identifier, ByteBuf data) {
    }

    @Override
    public void onPublish(PackageLevel level, boolean retain, Long identifier, ByteBuf data) {
    }

    @Override
    public void onPing(Long identifier, ByteBuf data) {

    }

    @Override
    public void onDisconnect(Long identifier, ByteBuf data) {

    }

    @Override
    public void sendPackage(BasePackage pkg) {
        channel.writeAndFlush(pkg.getByteBuf());
    }

    @Override
    public void disconnect() {

    }
}
