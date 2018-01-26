package com.nirvana.xin.codec.decoder;

import java.nio.charset.Charset;

/**
 * Created by Nirvana on 2018/1/15.
 */
public class UTF8StringDecoder extends AbstractDecoder<String> {

    private static final UTF8StringDecoder INSTANCE = new UTF8StringDecoder();

    private UTF8StringDecoder() {}

    public static UTF8StringDecoder getInstance() {
        return INSTANCE;
    }

    @Override
    public String decode(byte[] bytes, int pos, int length) {
        return new String(bytes, pos, length, Charset.forName("UTF8"));
    }

}
