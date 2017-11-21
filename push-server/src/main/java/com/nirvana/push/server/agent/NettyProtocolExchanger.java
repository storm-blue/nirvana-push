package com.nirvana.push.server.agent;

import com.nirvana.push.core.agent.ProtocolExchanger;
import com.nirvana.push.core.message.Package;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by Nirvana on 2017/11/22.
 */
public class NettyProtocolExchanger implements ProtocolExchanger {

    private SocketChannel channel;

    public NettyProtocolExchanger(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void output(Package pkg) {

    }

    @Override
    public void close() {
        channel.close();
    }

}
