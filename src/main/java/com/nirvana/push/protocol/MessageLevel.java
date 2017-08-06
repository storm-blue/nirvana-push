package com.nirvana.push.protocol;

import com.nirvana.push.utils.CodeEnumerator;

/**
 * 消息等级。
 * Created by Nirvana on 2017/8/2.
 */
public enum MessageLevel {

    /*不会确认*/
    NO_CONFIRM(0x00),

    /*至少一次*/
    AT_LEAST_ONCE(0x01),

    /*有且仅一次*/
    EXACTLY_ONCE(0x02);

    private int code;

    MessageLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final CodeEnumerator<MessageLevel> enumerator = new CodeEnumerator<>(MessageLevel.class);

    public static MessageLevel get(int code) {
        return enumerator.get(code);
    }

}
