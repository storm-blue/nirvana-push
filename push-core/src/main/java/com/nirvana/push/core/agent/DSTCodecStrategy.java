package com.nirvana.push.core.agent;

import com.nirvana.push.protocol.p2.DSTElement;
import com.nirvana.push.protocol.p2.DSTPackage;
import com.nirvana.push.protocol.p2.L2Element;
import com.nirvana.push.protocol.p2.L2Package;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * DST编解码策略。
 * Created by Nirvana on 2017/9/11.
 */
public class DSTCodecStrategy implements L2PCodecStrategy {

    @Override
    public L2Package decode(ByteBuf byteBuf) {
        return new DSTPackage(byteBuf.toString(Charset.forName("UTF-8")));
    }

    @Override
    public L2Package encodeValues(Object... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof String) {
                continue;
            }
            values[i] = values[i].toString();
        }
        return new DSTPackage((String[]) values);
    }

    @Override
    public L2Package getPackage(L2Element... elements) {
        return new DSTPackage((DSTElement[]) elements);
    }

    @Override
    public L2Element getElement(String key, Object value) {
        return new DSTElement(key, (String) value);
    }

}
