package com.nirvana.push.protocol;

import java.io.UnsupportedEncodingException;

/**
 * 包体。
 */
public class Body implements Byteable {

    private byte[] bytes;

    public Body(String content, String charset) {
        try {
            bytes = content.getBytes(charset);
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
}
