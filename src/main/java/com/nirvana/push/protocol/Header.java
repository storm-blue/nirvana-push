package com.nirvana.push.protocol;

/**
 * 协议包头。
 */
public class Header extends AbstactByteable {

    private byte[] bytes;

    public Header(byte[] bytes) {
        this.bytes = bytes;
    }

    public Header(MessageType type, MessageLevel level, MessageCharset charset, int length) {

    }

    public byte[] getBytes() {
        return bytes;
    }
}
