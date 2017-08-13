package com.nirvana.push.core.agent;

import com.nirvana.push.core.Message;
import com.nirvana.push.core.Publisher;
import com.nirvana.push.core.Subscriber;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

/**
 * 绑定
 * Created by Nirvana on 2017/8/13.
 */
public class SocketChannelAgent extends AbstractAgent implements Publisher, Subscriber {

    @Override
    public void publish(Message msg) {

    }

    @Override
    public void publish(Collection<Message> msg) {

    }

    @Override
    public void onMessage(Message msg) {

    }

    @Override
    void onConnect(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onSubscribe(boolean identifiable, Long identifier, ByteBuf data) {

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

    @Override
    void onPublish(PackageLevel level, boolean retain, boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onPing(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    void onDisconnect(boolean identifiable, Long identifier, ByteBuf data) {

    }

    @Override
    public void sendPackage(BasePackage pkg) {

    }
}
