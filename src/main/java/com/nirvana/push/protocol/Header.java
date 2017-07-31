package com.nirvana.push.protocol;

import com.nirvana.push.utils.BitRuler;

/**
 * 协议包头。
 */
public class Header extends AbstactByteable {

    private byte[] bytes;

    private MessageType messageType;

    private MessageLevel messageLevel;

    private MessageCharset messageCharset;

    private int packageLength = 0;

    public Header(byte[] bytes) {
        if (bytes.length != 3) {
            throw new IllegalArgumentException("package header must be 3 bytes");
        }
        this.bytes = bytes;
        messageType = MessageType.get(BitRuler.r(bytes[0], 6, 8));
        if (messageType == null) {
            throw new IllegalArgumentException("Wrong message type.");
        }
        messageLevel = MessageLevel.get(BitRuler.r(bytes[0], 4, 5));
        if (messageLevel == null) {
            throw new IllegalArgumentException("Wrong message level.");
        }
        messageCharset = MessageCharset.get(BitRuler.r(bytes[0], 1, 3));
        if (messageCharset == null) {
            throw new IllegalArgumentException("Wrong message charset.");
        }
        packageLength = bytes[1] << 8 | bytes[2];
    }

    public Header(MessageType type, MessageLevel level, MessageCharset charset, int length) {
        this.messageType = type;
        this.messageLevel = level;
        this.messageCharset = charset;
        this.packageLength = length;
        byte b1 = (byte) ((type.getCode() << 5) | (level.getCode() << 3) | charset.getCode());
        byte b2 = (byte) BitRuler.r(length, 9, 16);
        byte b3 = (byte) BitRuler.r(length, 1, 8);
        bytes = new byte[]{b1, b2, b3};
    }

    public Header() {
    }

    public byte[] getBytes() {
        return bytes;
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
}
