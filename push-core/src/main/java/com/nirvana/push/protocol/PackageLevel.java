package com.nirvana.push.protocol;

import com.nirvana.push.utils.CodeEnumerator;

/**
 * 消息等级。
 * Created by Nirvana on 2017/8/2.
 */
public enum PackageLevel {

    /*不会确认*/
    NO_CONFIRM(0x01),

    /*至少一次*/
    AT_LEAST_ONCE(0x02),

    /*有且仅一次*/
    EXACTLY_ONCE(0x03);

    private int code;

    PackageLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final CodeEnumerator<PackageLevel> enumerator = new CodeEnumerator<>(PackageLevel.class);

    public static PackageLevel get(int code) {
        return enumerator.get(code);
    }

}
