package com.nirvana.push.server.agent;

import com.nirvana.push.core.publisher.Publisher;
import com.nirvana.push.core.publisher.SimpleStringPublisher;
import com.nirvana.push.core.subscriber.StringSubscriber;
import com.nirvana.push.core.subscriber.Subscriber;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

import static com.nirvana.push.server.PushServer.PUBLIC_MESSAGE_BROKER;

/**
 * 绑定
 * Created by Nirvana on 2017/8/13.
 */
public class DefaultChannelAgent extends AbstractChannelAgent {

    private Subscriber subscriber = new StringSubscriber(this);

    private Publisher publisher = new SimpleStringPublisher(PUBLIC_MESSAGE_BROKER);

    public DefaultChannelAgent(SocketChannel channel) {
        super(channel);
    }

    @Override
    void onConnect(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onSubscribe(boolean identifiable, Long identifier, ByteBuf data) {
        PUBLIC_MESSAGE_BROKER.addSubscriber(subscriber);
    }

    @Override
    void onPushMessageAck(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onExactlyOnceMessageAck(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onUnsubscribe(boolean identifiable, Long identifier, ByteBuf data) {
    }

    @SuppressWarnings("unchecked")
    @Override
    void onPublish(PackageLevel level, boolean retain, boolean identifiable, Long identifier, ByteBuf data) {
        publisher.publish(new UTF8StringPayloadPart(data).getMessage());
    }

    @Override
    void onPing(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onDisconnect(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    public void sendPackage(BasePackage pkg) {
        getChannel().writeAndFlush(pkg.getByteBuf());
    }
}
