package com.nirvana.ezalor.protocol;

import io.netty.buffer.ByteBuf;

/**
 * 可字节化。
 * Created by Nirvana on 2017/8/2.
 */
public interface Outputable {

    ByteBuf getByteBuf();

}
