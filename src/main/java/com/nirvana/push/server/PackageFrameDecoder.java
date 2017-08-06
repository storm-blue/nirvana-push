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
 * 继承<code>ByteToMessageDecoder</code>的类不能被<code>@Sharable</code>注解。
 * 这就意味着永远只有一个线程操作此对象。可以忽略线程安全问题。
 *
 * @author Nirvana
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

    /*当进入recovery模式之后，记录可回收的数据。*/
    private CompositeByteBuf recyclable = Unpooled.compositeBuffer(4);

    public PackageFrameDecoder() {
    }

    public PackageFrameDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize > Package.MAX_LENGTH ? Package.MAX_LENGTH : maxFrameSize;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (recyclable.readableBytes() + in.readableBytes() >= currentNeedReadableSize) {

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
            }
        }
    }


    private void doHeaderProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = readRecyclableFirst(in, currentNeedReadableSize);
        try {
            header = new Header(buf);
            if (header.getPayloadSize() > maxFrameSize - Header.HEADER_SIZE - Footer.FOOT_SIZE) {
                switchToRecovery();
                return;
            }
            workMode = MODE_BODY_PROCESSING;
            currentNeedReadableSize = header.getPayloadSize();
        } catch (PackageParseException e) {
            switchToRecovery();
        }
    }

    private void doBodyProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = readRecyclableFirst(in, currentNeedReadableSize);
        body = new Body(buf, header.getMessageCharset().getCharset());
        workMode = MODE_FOOTER_PROCESSING;
        currentNeedReadableSize = Footer.FOOT_SIZE;
    }

    private void doFooterProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = readRecyclableFirst(in, currentNeedReadableSize);
        if (Footer.checkFooter(buf)) {
            Package p = new Package(header, body, footer);
            out.add(p);
            workMode = MODE_HEADER_PROCESSING;
            currentNeedReadableSize = Header.HEADER_SIZE;
        } else {
            if (recyclable.readableBytes() > 0) {
            }
        }
    }

    /**
     * 在回收数据和可读数据中读取字节。此方法会优先读取回收数据的字节。
     *
     * @param in   可读数据，如果回收数据字节不足够，则会在此ByteBuf中读取。
     * @param size 要读取的字节数。
     * @return 返回一个新创建的包含读取数据的ByteBuf对象。
     */
    private ByteBuf readRecyclableFirst(ByteBuf in, int size) {
        return Unpooled.wrappedBuffer(readRecyclableFirst0(in, size));
    }

    /**
     * 在回收数据和可读数据中读取字节。此方法会优先读取回收数据的字节。
     *
     * @param in   可读数据，如果回收数据字节不足够，则会在此ByteBuf中读取。
     * @param size 要读取的字节数。
     * @return 返回一个新创建的包含读取数据的byte数组。
     */
    private byte[] readRecyclableFirst0(ByteBuf in, int size) {
        byte[] bytes = new byte[size];
        int recyclableBytes = recyclable.readableBytes();
        if (size < recyclableBytes) {
            recyclable.readBytes(bytes);
        } else if (size == recyclableBytes) {
            recyclable.readBytes(bytes);
            recyclable.clear();
        } else {
            recyclable.readBytes(bytes, 0, recyclableBytes);
            recyclable.clear();
            in.readBytes(bytes, recyclableBytes, size - recyclableBytes);
        }
        return bytes;
    }

    private void switchToRecovery() {
        workMode = MODE_RECOVERY;
        currentNeedReadableSize = 1;
    }
}
