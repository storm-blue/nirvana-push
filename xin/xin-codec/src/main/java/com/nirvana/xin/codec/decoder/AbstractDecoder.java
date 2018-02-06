package com.nirvana.xin.codec.decoder;

/**
 * Created by Nirvana on 2018/1/16.
 */
public abstract class AbstractDecoder<T> implements Decoder<T> {

    @Override
    public T decode(byte[] bytes) {
        return decode(bytes, 0, bytes.length);
    }

    public abstract T decode(byte[] bytes, int pos, int length);

}