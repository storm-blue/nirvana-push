package com.nirvana.push.server.agent;

import com.nirvana.push.core.agent.SessionAgent;
import com.nirvana.push.protocol.BasePackage;
import io.netty.channel.socket.SocketChannel;

/**
 * 绑定
 * Created by Nirvana on 2017/8/13.
 */
public class DefaultAgent extends SessionAgent {

    private SocketChannel channel;

    public DefaultAgent(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void sendPackage(BasePackage pkg) {
        channel.writeAndFlush(pkg.getByteBuf());
    }

    @Override
    public void disconnect() {
        channel.close();
    }
}
