package com.nirvana.xin.core.message;

import com.nirvana.xin.utils.CodeEnumerator;

/**
 * 消息等级。
 * Created by Nirvana on 2017/8/2.
 */
public enum MessageLevel {

    /*不会确认*/
    NO_CONFIRM(0x01),

    /*至少一次*/
    AT_LEAST_ONCE(0x02),

    /*有且仅一次*/
    EXACTLY_ONCE(0x03);

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
