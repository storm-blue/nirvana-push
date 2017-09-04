package com.nirvana.push.protocol.decoder;

import com.nirvana.push.protocol.ScalableNumberPart;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.HeaderPart;
import com.nirvana.push.protocol.PayloadPart;
import com.nirvana.push.protocol.exception.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
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
    private int maxFrameSize = BasePackage.MAX_SIZE;

    /*当前所需读取的最小字节数*/
    private int currentNeedReadableSize = HeaderPart.HEADER_PART_SIZE;

    /**
     * 工作模式。
     * 当发现读取的包有问题的时候，进入错误处理模式。
     */
    private int workMode = MODE_HEADER_PROCESSING;

    /*header处理模式*/
    private static final int MODE_HEADER_PROCESSING = 1;
    /*lengthRemain处理模式*/
    private static final int MODE_LENGTH_REMAIN_PROCESSING = 2;
    /*identifier处理模式*/
    private static final int MODE_IDENTIFIER_PROCESSING = 3;
    /*payload处理模式*/
    private static final int MODE_PAYLOAD_PROCESSING = 4;
    /*error处理模式*/
    private static final int MODE_ERROR_PROCESSING = 5;

    /**
     * 是否停止工作。
     */
    private volatile boolean stop = false;


    /*Header*/
    private HeaderPart header;
    /*lengthRemain*/
    private ScalableNumberPart lengthRemain;
    /*identifier*/
    private ScalableNumberPart identifier;
    /*payload*/
    private PayloadPart payload;

    public PackageFrameDecoder() {
    }

    public PackageFrameDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize > BasePackage.MAX_SIZE ? BasePackage.MAX_SIZE : maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (in.readableBytes() >= currentNeedReadableSize && !stop) {

            //工作在header处理模式。
            if (workMode == MODE_HEADER_PROCESSING) {
                doHeaderProcessing(ctx, in, out);
            }

            //工作在lengthRemain处理模式。
            else if (workMode == MODE_LENGTH_REMAIN_PROCESSING) {
                doLengthRemainProcessing(ctx, in, out);
            }

            //工作在identifier处理模式。
            else if (workMode == MODE_IDENTIFIER_PROCESSING) {
                doIdentifierProcessing(ctx, in, out);
            }

            //工作在payload处理模式。
            else if (workMode == MODE_PAYLOAD_PROCESSING) {
                doPayloadProcessing(ctx, in, out);
            }

            //工作在error处理模式。
            else if (workMode == MODE_ERROR_PROCESSING) {
                doErrorProcessing(ctx, in, out);
                break;
            }
        }
    }

    /**
     * MODE_HEADER_PROCESSING模式。
     * 读取Header，如果读取成功，进入MODE_LENGTH_REMAIN_PROCESSING模式。否则进入错误处理模式。
     */
    private void doHeaderProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            in.markReaderIndex();
            byte b = in.readByte();
            header = new HeaderPart(b);
            LOGGER.debug("Header处理成功，进入MODE_LENGTH_REMAIN_PROCESSING模式。");
            switchToLengthRemainProcessingMode();
        } catch (ProtocolException e) {
            LOGGER.debug("包解析错误，进入错误处理模式：", e);
            in.resetReaderIndex();
            switchToErrorProcessingMode();
        }
    }

    /**
     * MODE_LENGTH_REMAIN_PROCESSING模式.
     * 读取LengthRemain，如果读取成功，进入MODE_IDENTIFIER_PROCESSING工作模式。否则进入错误处理模式。
     */
    private void doLengthRemainProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        for (int i = 0; i < in.readableBytes(); i++) {
            try {
                byte b = in.readByte();
                boolean finish = lengthRemain.append(b);
                if (finish) {
                    /*如果包总长度大于设定的最大长度。*/
                    if (HeaderPart.HEADER_PART_SIZE + lengthRemain.getSize() + lengthRemain.getValue() > maxFrameSize) {
                        throw new ProtocolException("帧解析错误，超出最大设定长度：" + maxFrameSize);
                    }
                    if (header.isIdentifiable()) {
                        LOGGER.debug("LengthRemain处理成功，进入MODE_IDENTIFIER_PROCESSING模式。");
                        switchToIdentifierProcessingMode();
                        return;
                    } else {
                        LOGGER.debug("LengthRemain处理成功，进入MODE_PAYLOAD_PROCESSING模式。");
                        switchToPayloadProcessingMode();
                        return;
                    }
                }
            } catch (ProtocolException e) {
                LOGGER.debug("包解析错误，进入错误处理模式：", e);
                in.resetReaderIndex();
                switchToErrorProcessingMode();
                return;
            }
        }
    }

    //当前顺序号部分字节长度
    private int identifierLength = 0;

    /**
     * MODE_IDENTIFIER_PROCESSING模式.
     * 读取Identifier，如果读取成功，进入MODE_PAYLOAD_PROCESSING模式。否则进入错误处理。
     */
    private void doIdentifierProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        for (int i = 0; i < in.readableBytes(); i++) {
            try {
                byte b = in.readByte();
                boolean finish = identifier.append(b);
                if (finish) {
                    LOGGER.debug("Identifier处理成功，进入MODE_PAYLOAD_PROCESSING模式。");
                    identifierLength = identifier.getSize();
                    switchToPayloadProcessingMode();
                    return;
                }
            } catch (ProtocolException e) {
                LOGGER.debug("包解析错误，进入错误处理模式：", e);
                in.resetReaderIndex();
                switchToErrorProcessingMode();
                return;
            }
        }
    }

    /**
     * MODE_PAYLOAD_PROCESSING模式.
     * 读取Payload，输出Package，并重新进入MODE_HEADER_PROCESSING模式。
     */
    private void doPayloadProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ByteBuf buf = in.readBytes(currentNeedReadableSize);
        payload = new PayloadPart(buf);
        out.add(buildPackage());
        LOGGER.debug("Payload处理成功，进入MODE_HEADER_PROCESSING模式。");
        switchToHeaderProcessingMode();
    }

    /**
     * MODE_ERROR_PROCESSING模式。
     * 在此进行协议的错误处理，此处只是简单的关闭channel.
     */
    private void doErrorProcessing(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        LOGGER.error("协议错误，关闭channel.");
        ChannelFuture future = ctx.close();
        future.addListener(future0 -> LOGGER.info("强制关闭连接。"));
        stop();
    }

    /*切换到LengthRemain处理模式*/
    private void switchToLengthRemainProcessingMode() {
        workMode = MODE_LENGTH_REMAIN_PROCESSING;
        currentNeedReadableSize = 1;

        //LengthRemain部分最大长度4字节。
        lengthRemain = ScalableNumberPart.startCreation(4);
    }

    /*切换到Identifier处理模式*/
    private void switchToIdentifierProcessingMode() {
        workMode = MODE_IDENTIFIER_PROCESSING;
        currentNeedReadableSize = 1;

        //Identifier部分最大长度6字节。
        identifier = ScalableNumberPart.startCreation(6);
    }

    /*切换至Payload处理模式，如果负载字节数为0，抛出异常。*/
    private void switchToPayloadProcessingMode() {
        workMode = MODE_PAYLOAD_PROCESSING;
        int payloadLength = (int) lengthRemain.getValue() - identifierLength;
        if (payloadLength <= 0) {
            throw new ProtocolException("负载长度不能为0");
        }
        currentNeedReadableSize = payloadLength;
    }

    /*切换到Header处理模式*/
    private void switchToHeaderProcessingMode() {
        workMode = MODE_HEADER_PROCESSING;
        currentNeedReadableSize = HeaderPart.HEADER_PART_SIZE;
    }

    /*切换到Error处理模式*/
    private void switchToErrorProcessingMode() {
        workMode = MODE_ERROR_PROCESSING;
        currentNeedReadableSize = 1;
    }

    private BasePackage buildPackage() {
        return new BasePackage(header, lengthRemain, identifier, payload);
    }

    protected void stop() {
        stop = true;
    }
}
