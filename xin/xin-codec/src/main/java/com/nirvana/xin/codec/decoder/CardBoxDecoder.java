package com.nirvana.xin.codec.decoder;

import com.nirvana.xin.codec.l2.CardBoxL2Codec;
import com.nirvana.purist.core.message.CardBox;

/**
 * Created by Nirvana on 2018/1/15.
 */
public class CardBoxDecoder extends AbstractDecoder<CardBox> {

    private static final CardBoxDecoder INSTANCE = new CardBoxDecoder();

    private CardBoxDecoder() {}

    public static CardBoxDecoder getInstance() {
        return INSTANCE;
    }

    private UTF8StringDecoder stringDecoder = UTF8StringDecoder.getInstance();

    private CardBoxL2Codec l2Codec = CardBoxL2Codec.getInstance();

    @Override
    public CardBox decode(byte[] bytes, int pos, int length) {
        String content = stringDecoder.decode(bytes, pos, length);
        return l2Codec.decode(content);
    }

}
