package com.nirvana.xin.codec.encoder;

import com.nirvana.xin.codec.l2.CardBoxL2Codec;
import com.nirvana.purist.core.message.CardBox;

/**
 * Created by Nirvana on 2018/1/15.
 */
public class CardBoxEncoder implements Encoder<CardBox> {

    private static final CardBoxEncoder INSTANCE = new CardBoxEncoder();

    private CardBoxL2Codec codec = CardBoxL2Codec.getInstance();

    private UTF8StringEncoder stringEncoder = UTF8StringEncoder.getInstance();

    private CardBoxEncoder() {}

    public static CardBoxEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] encode(CardBox object) {
        String content = codec.encode(object);
        return stringEncoder.encode(content);
    }
}
