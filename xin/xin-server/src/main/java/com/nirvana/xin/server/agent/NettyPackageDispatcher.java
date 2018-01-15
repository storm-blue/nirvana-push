package com.nirvana.xin.server.agent;

import com.nirvana.xin.core.agent.PackageDispatcher;
import com.nirvana.xin.core.message.Package;
import com.nirvana.xin.protocol.ProtocolPackage;
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
