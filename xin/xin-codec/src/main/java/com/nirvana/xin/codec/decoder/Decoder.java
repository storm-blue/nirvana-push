package com.nirvana.xin.codec.decoder;

/**
 * Created by Nirvana on 2018/1/15.
 */
public interface Decoder<T> {

    T decode(byte[] bytes);

}
