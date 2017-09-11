package com.nirvana.push.core.agent;

import com.nirvana.push.protocol.l2.L2Package;
import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * 二级协议编解码策略。
 * Created by Nirvana on 2017/9/10.
 */
public interface L2PCodecStrategy {

    L2Package decode(ByteBuf byteBuf);

    L2Package encodeValues(Object... values);

    L2Package encodeValues(Map<String, Object> values);

}