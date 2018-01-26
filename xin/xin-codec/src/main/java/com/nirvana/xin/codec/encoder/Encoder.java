package com.nirvana.xin.codec.encoder;

/**
 * Created by Nirvana on 2018/1/15.
 */
public interface Encoder<T> {

    byte[] encode(T object);

}
