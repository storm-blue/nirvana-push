package com.nirvana.push.server.agent;

import com.nirvana.push.core.agent.AbstractSessionAgent;
import com.nirvana.push.core.agent.ProtocolExchanger;
import com.nirvana.push.core.message.Package;
import io.netty.channel.socket.SocketChannel;

/**
 * 绑定
 * Created by Nirvana on 2017/8/13.
 */
public class DefaultAgent extends AbstractSessionAgent {

    public DefaultAgent(ProtocolExchanger exchanger) {
        super(exchanger);
    }

    @Override
    public void sendPackage(Package pkg) {

    }
}
