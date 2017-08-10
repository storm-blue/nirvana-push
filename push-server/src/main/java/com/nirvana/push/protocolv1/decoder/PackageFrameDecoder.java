package com.nirvana.push.protocolv1.decoder;

import com.nirvana.push.protocolv1.Body;
import com.nirvana.push.protocolv1.Footer;
import com.nirvana.push.protocolv1.Header;
import com.nirvana.push.protocolv1.Package;
import com.nirvana.push.protocol.exception.ProtocolException;
import io.netty.buffer.ByteBuf;
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

    /*当前所需读取的最小字节数*/
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

    /**
     * MODE_HEADER_PROCESSING模式。
     * 读取Header，如果读取成功，进入body处理模式。否则进入恢复模式。
     */
    private void doHeaderProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        try {
            header = new Header(buf);
            if (header.getPayloadSize() > maxFrameSize - Header.HEADER_SIZE - Footer.FOOT_SIZE) {
                in.resetReaderIndex();
                switchToRecovery();
                return;
            }
            workMode = MODE_BODY_PROCESSING;
            currentNeedReadableSize = header.getPayloadSize();
            LOGGER.debug("Header处理成功，进入MODE_BODY_PROCESSING模式。");
        } catch (ProtocolException e) {
            in.resetReaderIndex();
            switchToRecovery();
        }
    }

    /**
     * MODE_BODY_PROCESSING模式.
     * 读取Body，如果读取成功，进入MODE_FOOTER_PROCESSING工作模式。否则进入恢复模式。
     */
    private void doBodyProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        body = new Body(buf, header.getMessageCharset().getCharset());
        workMode = MODE_FOOTER_PROCESSING;
        currentNeedReadableSize = Footer.FOOT_SIZE;
        LOGGER.debug("Body处理成功，进入MODE_FOOTER_PROCESSING模式。");
    }

    /**
     * MODE_FOOTER_PROCESSING模式.
     * 读取Body，如果读取成功，输出Package，并重新进入MODE_HEADER_PROCESSING模式。否则进入恢复模式。
     */
    private void doFooterProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        if (Footer.checkFooter(buf)) {
            Package p = new Package(header, body, footer);
            out.add(p);
            workMode = MODE_HEADER_PROCESSING;
            currentNeedReadableSize = Header.HEADER_SIZE;
            LOGGER.debug("帧解码成功，重新进入MODE_HEADER_PROCESSING模式。");
        } else {
            in.resetReaderIndex();
            switchToRecovery();
        }
    }


    private int recoveryFlag = 0;

    /**
     * 检测是否连续读到0xff,0xff分隔符。
     * 如果读到分隔符，恢复完毕，进入MODE_HEADER_PROCESSING处理模式。
     */
    private void doRecovery(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        for (int i = 0; i < in.readableBytes(); i++) {
            byte b = in.readByte();
            if (b == (byte) 0xff) {
                recoveryFlag++;
            } else {
                recoveryFlag = 0;
            }
            if (recoveryFlag == 2) {
                workMode = MODE_HEADER_PROCESSING;
                currentNeedReadableSize = Header.HEADER_SIZE;
                LOGGER.debug("恢复成功，进入MODE_HEADER_PROCESSING模式。");
                break;
            }
        }
    }

    //切换到恢复模式。
    private void switchToRecovery() {
        workMode = MODE_RECOVERY;
        currentNeedReadableSize = 1;
        LOGGER.debug("进入Recovery模式。");
    }
}
