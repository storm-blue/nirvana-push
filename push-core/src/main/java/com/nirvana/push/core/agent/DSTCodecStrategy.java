package com.nirvana.push.core.agent;

import com.nirvana.push.protocol.l2.DSTPackage;
import com.nirvana.push.protocol.l2.L2Package;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * DST编解码策略。
 * Created by Nirvana on 2017/9/11.
 */
public class DSTCodecStrategy implements L2PCodecStrategy {

    private DSTCodecStrategy() {
    }

    private static DSTCodecStrategy STRATEGY = new DSTCodecStrategy();

    public static DSTCodecStrategy getStrategy() {
        return STRATEGY;
    }

    @Override
    public L2Package decode(ByteBuf byteBuf) {
        return new DSTPackage(byteBuf.toString(Charset.forName("UTF-8")));
    }

    @Override
    public L2Package encodeValues(Object... values) {
        return new DSTPackage(values);
    }

    @Override
    public L2Package encodeValues(Map<String, Object> values) {
        return new DSTPackage(values);
    }
}
