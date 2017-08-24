package com.nirvana.push.corex.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 绑定
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class NettyAttrManager {

    public static final String CLIENT = "client";//客户端ID

    private static final AttributeKey<Object> ATTR_KEY_CLIENT = AttributeKey.valueOf(CLIENT);

    public static Client getAttrClientId(Channel channel) {
        return (Client) channel.attr(NettyAttrManager.ATTR_KEY_CLIENT).get();
    }

    public static void setAttrClientId(Channel channel, Client client) {
        channel.attr(NettyAttrManager.ATTR_KEY_CLIENT).set(client);
    }

}
