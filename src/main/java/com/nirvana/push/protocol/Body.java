package com.nirvana.push.protocol;

import java.io.UnsupportedEncodingException;

/**
 * 包体。
 */
public class Body extends AbstactByteable {

    private String content;

    private String charset;

    public Body(byte[] bytes, int index, int length, String charset) {
        try {
            this.charset = charset;
            setBytes(bytes, index, length);
            content = new String(bytes, index, length, charset);
        } catch (UnsupportedEncodingException ignore) {
        }
    }

    public Body(String content, String charset) {
        this.content = content;
        try {
            byte[] bytes = content.getBytes(charset);
            setBytes(bytes, 0, bytes.length);
            this.charset = charset;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncoding:" + charset);
        }
    }

    public String getContent() {
        return content;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public String toString() {
        return "Body{" +
                "content='" + content + '\'' +
                ", charset='" + charset + '\'' +
                '}';
    }
}
