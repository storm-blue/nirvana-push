package com.nirvana.push.corex.session;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
 * NioSocketchannelSession实现
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class NioSocketChannelSession implements Session {

    public NioSocketChannelSession(Channel channel) {
        this.channel = channel;
        this.sessionId = sessionId;
    }

    /**
     * 绑定对象key
     */
    public static AttributeKey<Client> ATTACHMENT_KEY = AttributeKey.valueOf("client");

    /**
     * 实际会话对象
     */
    private Channel channel;

    private Long sessionId;

    @Override
    public Client getClient() {
        return channel.attr(ATTACHMENT_KEY).get();
    }

    @Override
    public void bindClient(Client attachment) {
        channel.attr(ATTACHMENT_KEY).set(attachment);
    }

    @Override
    public void removeClient() {
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
        System.out.println("是否活跃 >" + channel.isActive());
        System.out.println("是否可写 >" + channel.isWritable());

        channel.writeAndFlush(Unpooled.copiedBuffer((String) message, CharsetUtil.UTF_8)).addListener(future -> System.out.println("成功写入消息 >> " + message));
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
