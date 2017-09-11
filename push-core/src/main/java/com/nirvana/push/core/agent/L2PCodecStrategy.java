package com.nirvana.push.core.agent;

import com.nirvana.push.protocol.p2.L2Element;
import com.nirvana.push.protocol.p2.L2Package;
import io.netty.buffer.ByteBuf;

/**
 * 二级协议编解码策略。
 * Created by Nirvana on 2017/9/10.
 */
public interface L2PCodecStrategy {

    L2Package decode(ByteBuf byteBuf);

    L2Package encodeValues(Object... values);

    L2Package getPackage(L2Element... elements);

    L2Element getElement(String key, Object value);

}