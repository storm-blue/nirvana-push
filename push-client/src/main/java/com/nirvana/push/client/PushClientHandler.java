package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.p2.DSTPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class PushClientHandler extends SimpleChannelInboundHandler<BasePackage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePackage msg) throws Exception {
        if (msg.getPackageType() == PackageType.PUSH_MESSAGE) {
            String content = msg.getPayload().getByteBuf().toString(Charset.forName("UTF-8"));
            DSTPackage dstPackage = new DSTPackage(content);
            String message = dstPackage.get(0);
            System.out.println("receive : " + message);
        }
    }
}
