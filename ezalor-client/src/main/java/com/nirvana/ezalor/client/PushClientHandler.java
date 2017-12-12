package com.nirvana.ezalor.client;

import com.nirvana.ezalor.core.message.PackageType;
import com.nirvana.ezalor.protocol.ProtocolPackage;
import com.nirvana.ezalor.protocol.l2.DSTPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class PushClientHandler extends SimpleChannelInboundHandler<ProtocolPackage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolPackage msg) throws Exception {
        if (msg.getPackageType() == PackageType.PUSH_MESSAGE) {
            String content = msg.getPayload().getByteBuf().toString(Charset.forName("UTF-8"));
            DSTPackage dstPackage = new DSTPackage(content);
            //System.out.println(dstPackage.getCardBox().getCard(0).getContent());
        }
        msg.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("遇到IO异常，关闭连接。");
        PushClient.channels.remove(ctx.channel());
        ctx.close();
    }
}
