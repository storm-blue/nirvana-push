package com.nirvana.push.server.agent;

import com.nirvana.push.core.agent.PackageDispatcher;
import com.nirvana.push.core.message.Package;
import com.nirvana.push.protocol.ProtocolPackage;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by Nirvana on 2017/11/22.
 */
public class NettyPackageDispatcher implements PackageDispatcher {

    private SocketChannel channel;

    public NettyPackageDispatcher(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void dispatch(Package pkg) {
        ProtocolPackage protocolPackage = ProtocolPackage.fromPackage(pkg);
        channel.writeAndFlush(protocolPackage.getByteBuf());
    }

    @Override
    public void close() {
        channel.close();
    }

}
