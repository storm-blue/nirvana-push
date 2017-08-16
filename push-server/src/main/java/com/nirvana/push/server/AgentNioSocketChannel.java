package com.nirvana.push.server;

import com.nirvana.push.server.agent.Agent;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * 绑定Agent的NioSocketChannel.
 * Created by Nirvana on 2017/8/17.
 */
public class AgentNioSocketChannel extends NioSocketChannel {

    private Agent agent;

    public AgentNioSocketChannel() {
    }

    public AgentNioSocketChannel(SelectorProvider provider) {
        super(provider);
    }

    public AgentNioSocketChannel(SocketChannel socket) {
        super(socket);
    }

    public AgentNioSocketChannel(Channel parent, SocketChannel socket) {
        super(parent, socket);
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
