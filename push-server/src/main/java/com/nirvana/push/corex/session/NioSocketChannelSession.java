package com.nirvana.push.corex.session;

import com.nirvana.push.corex.publisher.Publisher;
import com.nirvana.push.corex.subscriber.Subscriber;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Vector;

/**
 *  NioSocketchannelSession实现
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class NioSocketChannelSession implements Session,Subscriber,Publisher {

    public NioSocketChannelSession(Channel channel) {
        this.channel = channel;
        this.sessionId = sessionId;
    }

    /**
     * 绑定对象key
     */
    public static AttributeKey<Object> ATTACHMENT_KEY  = AttributeKey.valueOf("ATTACHMENT_KEY");

    /**
     * 实际会话对象
     */
    private Channel channel;

    private Long sessionId;

    @Override
    public Object getAttachment() {
        return channel.attr(ATTACHMENT_KEY).get();
    }

    @Override
    public void setAttachment(Object attachment) {
        channel.attr(ATTACHMENT_KEY).set(attachment);
    }

    @Override
    public void removeAttachment() {
        channel.attr(ATTACHMENT_KEY).remove();
    }

    @Override
    public void write(Object message) {
        channel.writeAndFlush(message);
    }

    @Override
    public boolean isConnected() {
        return channel.isActive();
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public void onMessage(Object message) {

    }


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
