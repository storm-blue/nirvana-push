package com.nirvana.push.protocol;

import com.nirvana.push.protocol.exception.HeaderParseException;
import com.nirvana.push.utils.BitRuler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * 协议包头。
 */
public class Header extends AbstactOutputable {

    public static final int HEADER_SIZE = 3;

    private MessageType messageType;

    private MessageLevel messageLevel;

    private MessageCharset messageCharset;

    private int payloadSize = 0;

    private ByteBuf buf;

    public Header(ByteBuf byteBuf) {
        if (HEADER_SIZE > byteBuf.readableBytes()) {
            throw new HeaderParseException("package header must be 3 bytes");
        }
        int headerInt = byteBuf.getMedium(0);
        messageType = MessageType.get(BitRuler.r(headerInt, 22, 24));
        if (messageType == null) {
            throw new HeaderParseException("Wrong message type.");
        }
        messageLevel = MessageLevel.get(BitRuler.r(headerInt, 20, 21));
        if (messageLevel == null) {
            throw new HeaderParseException("Wrong message level.");
        }
        messageCharset = MessageCharset.get(BitRuler.r(headerInt, 17, 19));
        if (messageCharset == null) {
            throw new HeaderParseException("Wrong message charset.");
        }
        payloadSize = BitRuler.r(headerInt, 1, 16);
        buf = byteBuf;
    }

    public Header(byte[] bytes) {
        this(bytes, 0);
    }

    public Header(byte[] bytes, int index) {
        this(Unpooled.wrappedBuffer(bytes, index, HEADER_SIZE));
    }

    public Header(MessageType type, MessageLevel level, MessageCharset charset, int length) {
        this.messageType = type;
        this.messageLevel = level;
        this.messageCharset = charset;
        this.payloadSize = length;
        byte b1 = (byte) ((type.getCode() << 5) | (level.getCode() << 3) | charset.getCode());
        byte b2 = (byte) BitRuler.r(length, 9, 16);
        byte b3 = (byte) BitRuler.r(length, 1, 8);
        buf = Unpooled.wrappedBuffer(new byte[]{b1, b2, b3});
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public MessageLevel getMessageLevel() {
        return messageLevel;
    }

    public MessageCharset getMessageCharset() {
        return messageCharset;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    @Override
    public ByteBuf getByteBuf() {
        return buf;
    }

    @Override
    public String toString() {
        return "Header{" +
                "messageType=" + messageType +
                ", messageLevel=" + messageLevel +
                ", messageCharset=" + messageCharset +
                ", payloadSize=" + payloadSize +
                '}';
    }
}
