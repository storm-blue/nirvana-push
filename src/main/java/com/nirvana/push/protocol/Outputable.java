package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;

/**
 * 可字节化。
 */
public interface Outputable {

    ByteBuf getByteBuf();

}
