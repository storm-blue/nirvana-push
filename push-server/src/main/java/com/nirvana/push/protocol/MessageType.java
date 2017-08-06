package com.nirvana.push.protocol;

import com.nirvana.push.utils.CodeEnumerator;

/**
 * 包类型。
 * Created by Nirvana on 2017/8/2.
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

    private static final CodeEnumerator<MessageType> enumerator = new CodeEnumerator<>(MessageType.class);

    public static MessageType get(int code) {
        return enumerator.get(code);
    }
}
