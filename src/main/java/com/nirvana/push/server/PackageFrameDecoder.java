package com.nirvana.push.server;

import com.nirvana.push.protocol.Footer;
import com.nirvana.push.protocol.Header;
import com.nirvana.push.protocol.Package;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PackageFrameDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageFrameDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (in.readableBytes() >= 3) {
            byte[] bytes = new byte[3];
            in.getBytes(0, bytes);
            Header header = new Header(bytes);

            LOGGER.info("开始解析包头：{}", header);
            int packageLength = Header.HEADER_SIZE + header.getPackageLength() + Footer.FOOT_SIZE;
            if (in.readableBytes() < packageLength) {
                break;
            }
            byte[] bytes0 = new byte[packageLength];
            in.readBytes(bytes0);
            Package p = new Package(bytes0);

            LOGGER.info("接收到完整协议包：{}", header);
            out.add(p);
        }
    }
}
