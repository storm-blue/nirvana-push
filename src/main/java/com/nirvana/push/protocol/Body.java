package com.nirvana.push.protocol;

import java.io.UnsupportedEncodingException;

/**
 * 包体。
 */
public class Body extends AbstactByteable {

    private byte[] bytes;

    private String content;

    private String charset;

    public Body(String content, String charset) {
        this.content = content;
        try {
            bytes = content.getBytes(charset);
            this.charset = charset;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncoding:" + charset);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int size() {
        return bytes.length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharset() {
        return charset;
    }
}
