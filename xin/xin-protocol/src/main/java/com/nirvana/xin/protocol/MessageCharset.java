package com.nirvana.xin.protocol;

import com.nirvana.xin.utils.CodeEnumerator;

/**
 * 消息编码。
 * Created by Nirvana on 2017/8/1.
 */
public enum MessageCharset {

    UTF8(0x01, "UTF-8"), UTF16(0x02, "UTF-16"), GB2312(0x03, "GB2312"), GB18030(0x04, "GB18030");

    private int code;

    private String charset;

    MessageCharset(int code, String charset) {
        this.code = code;
        this.charset = charset;
    }

    public int getCode() {
        return code;
    }

    public String getCharset() {
        return charset;
    }

    private static final CodeEnumerator<MessageCharset> enumerator = new CodeEnumerator<>(MessageCharset.class);

    public static MessageCharset get(int code) {
        return enumerator.get(code);
    }
}
