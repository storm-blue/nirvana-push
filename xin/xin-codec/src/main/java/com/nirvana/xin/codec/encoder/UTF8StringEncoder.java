package com.nirvana.xin.codec.encoder;

import java.nio.charset.Charset;

/**
 * Created by Nirvana on 2018/1/15.
 */
public class UTF8StringEncoder implements Encoder<String> {

    private static final UTF8StringEncoder INSTANCE = new UTF8StringEncoder();

    private UTF8StringEncoder() {}

    public static UTF8StringEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] encode(String object) {
        return object.getBytes(Charset.forName("UTF8"));
    }

}
