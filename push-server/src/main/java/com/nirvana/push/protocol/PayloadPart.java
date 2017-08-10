package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;

/**
 * com.nirvana.push.protocol.PayloadPart.java.
 * <p>
 * Created by Nirvana on 2017/8/9.
 */
public abstract class PayloadPart extends AbstractOutputable {

    @Override
    public ByteBuf getByteBuf() {
        return null;
    }
}
