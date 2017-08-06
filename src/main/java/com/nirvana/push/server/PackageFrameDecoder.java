package com.nirvana.push.server;

import com.nirvana.push.protocol.*;
import com.nirvana.push.protocol.Package;
import com.nirvana.push.protocol.exception.PackageParseException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 包的帧解码器。
 * <p>
 * 继承<code>ByteToMessageDecoder</code>的类不能被<code>@Sharable</code>注解。
 * 这就意味着永远只有一个线程操作此对象。可以忽略线程安全问题。
 * <p>
 * Created by Nirvana on 2017/8/4.
 */
public class PackageFrameDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageFrameDecoder.class);

    /*单帧最大长度*/
    private int maxFrameSize = Package.MAX_LENGTH;

    private int currentNeedReadableSize = Header.HEADER_SIZE;

    /**
     * 工作模式。
     * 当发现读取的包有问题的时候，进入恢复模式，直到重新读取到正确的包。
     */
    private int workMode = MODE_HEADER_PROCESSING;

    /*Header处理模式*/
    private static final int MODE_HEADER_PROCESSING = 1;
    /*Body处理模式*/
    private static final int MODE_BODY_PROCESSING = 2;
    /*Footer处理模式*/
    private static final int MODE_FOOTER_PROCESSING = 3;
    /*恢复模式*/
    private static final int MODE_RECOVERY = 0;


    /*Header*/
    private Header header;
    /*Body*/
    private Body body;
    /*Footer*/
    private Footer footer = Footer.getFooter();

    public PackageFrameDecoder() {
    }

    public PackageFrameDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize > Package.MAX_LENGTH ? Package.MAX_LENGTH : maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (in.readableBytes() >= currentNeedReadableSize) {

            //工作在Header处理模式。
            if (workMode == MODE_HEADER_PROCESSING) {
                doHeaderProcessing(ctx, in, out);
            }

            //工作在Body处理模式。
            else if (workMode == MODE_BODY_PROCESSING) {
                doBodyProcessing(ctx, in, out);
            }

            //工作在Footer处理模式。
            else if (workMode == MODE_FOOTER_PROCESSING) {
                doFooterProcessing(ctx, in, out);
            }

            //工作在恢复模式。
            else if (workMode == MODE_RECOVERY) {
                doRecovery(ctx, in, out);
            }
        }
    }

    private void doHeaderProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        try {
            header = new Header(buf);
            if (header.getPayloadSize() > maxFrameSize - Header.HEADER_SIZE - Footer.FOOT_SIZE) {
                switchToRecovery(in);
                return;
            }
            workMode = MODE_BODY_PROCESSING;
            currentNeedReadableSize = header.getPayloadSize();
        } catch (PackageParseException e) {
            switchToRecovery(in);
        }
    }

    private void doBodyProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        body = new Body(buf, header.getMessageCharset().getCharset());
        workMode = MODE_FOOTER_PROCESSING;
        currentNeedReadableSize = Footer.FOOT_SIZE;
    }

    private void doFooterProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        if (Footer.checkFooter(buf)) {
            Package p = new Package(header, body, footer);
            out.add(p);
            workMode = MODE_HEADER_PROCESSING;
            currentNeedReadableSize = Header.HEADER_SIZE;
        } else {
            switchToRecovery(in);
        }
    }

    private void doRecovery(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        if (Footer.checkFooter(buf)) {
            Package p = new Package(header, body, footer);
            out.add(p);
            workMode = MODE_HEADER_PROCESSING;
            currentNeedReadableSize = Header.HEADER_SIZE;
        } else {
            switchToRecovery(in);
        }
    }

    private void switchToRecovery(ByteBuf in) {
        in.resetReaderIndex();
        workMode = MODE_RECOVERY;
        currentNeedReadableSize = 1;
    }
}
