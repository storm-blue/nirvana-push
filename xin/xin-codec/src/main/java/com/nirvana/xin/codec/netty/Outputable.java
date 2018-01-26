package com.nirvana.xin.codec.netty;

import io.netty.buffer.ByteBuf;

/**
 * 可字节化。
 * Created by Nirvana on 2017/8/2.
 */
public interface Outputable {

    ByteBuf getByteBuf();

}
