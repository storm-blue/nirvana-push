package com.nirvana.push.server;

import com.nirvana.push.server.agent.DefaultChannelAgent;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * 继承默认的NioServerSocketChannel类，增加Agent绑定。
 * Created by Nirvana on 2017/8/17.
 */
public class AgentNioServerSocketChannel extends NioServerSocketChannel {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);

    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch = SocketUtils.accept(javaChannel());

        try {
            if (ch != null) {
                AgentNioSocketChannel channel = new AgentNioSocketChannel(this, ch);
                channel.setAgent(new DefaultChannelAgent(channel));
                buf.add(channel);
                return 1;
            }
        } catch (Throwable t) {
            logger.warn("Failed to create a new channel from an accepted socket.", t);

            try {
                ch.close();
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        }

        return 0;
    }
}
