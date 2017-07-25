package com.nirvana.push.protocol;

/**
 * 消息等级。
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


}
