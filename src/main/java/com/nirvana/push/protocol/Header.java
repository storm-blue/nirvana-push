package com.nirvana.push.protocol;

import com.nirvana.push.utils.BitRuler;

import java.util.Arrays;

/**
 * 协议包头。
 */
public class Header extends AbstactByteable {

    public static final int HEADER_SIZE = 3;

    private MessageType messageType;

    private MessageLevel messageLevel;

    private MessageCharset messageCharset;

    private int packageLength = 0;

    public Header(byte[] bytes) {
        this(bytes, 0);
    }

    public Header(byte[] bytes, int index) {
        if (index + HEADER_SIZE > bytes.length) {
            throw new IllegalArgumentException("package header must be 3 bytes");
        }

        setBytes(bytes, 0, HEADER_SIZE);
        messageType = MessageType.get(BitRuler.r(bytes[index], 6, 8));
        if (messageType == null) {
            throw new IllegalArgumentException("Wrong message type.");
        }
        messageLevel = MessageLevel.get(BitRuler.r(bytes[index], 4, 5));
        if (messageLevel == null) {
            throw new IllegalArgumentException("Wrong message level.");
        }
        messageCharset = MessageCharset.get(BitRuler.r(bytes[index], 1, 3));
        if (messageCharset == null) {
            throw new IllegalArgumentException("Wrong message charset.");
        }
        packageLength = bytes[index + 1] << 8 | bytes[index + 2];
    }

    public Header(MessageType type, MessageLevel level, MessageCharset charset, int length) {
        this.messageType = type;
        this.messageLevel = level;
        this.messageCharset = charset;
        this.packageLength = length;
        byte b1 = (byte) ((type.getCode() << 5) | (level.getCode() << 3) | charset.getCode());
        byte b2 = (byte) BitRuler.r(length, 9, 16);
        byte b3 = (byte) BitRuler.r(length, 1, 8);
        setBytes(new byte[]{b1, b2, b3});
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

    public int getPackageLength() {
        return packageLength;
    }

    @Override
    public String toString() {
        return "Header{" +
                "messageType=" + messageType +
                ", messageLevel=" + messageLevel +
                ", messageCharset=" + messageCharset +
                ", packageLength=" + packageLength +
                '}';
    }
}
