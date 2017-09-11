package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.l2.DSTPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class PushClientHandler extends SimpleChannelInboundHandler<BasePackage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePackage msg) throws Exception {
        if (msg.getPackageType() == PackageType.PUSH_MESSAGE) {
            String content = msg.getPayload().getByteBuf().toString(Charset.forName("UTF-8"));
            DSTPackage dstPackage = new DSTPackage(content);
            String message = dstPackage.get(0);
            //System.out.println("receive : " + message);
        }
        msg.release();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("遇到IO异常，关闭连接。");
        ctx.close();
        PushClient.channels.remove(ctx.channel().id());
    }
}
