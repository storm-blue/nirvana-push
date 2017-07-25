package com.nirvana.push.protocol;

/**
 * 包类型。
 */
public enum MessageType {

    HEART_BEAT(0x00), PAYLOAD(0x01), RESPONSE(0x02);

    private int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
