package com.nirvana.xin.codec.encoder;

import com.nirvana.xin.codec.NumberCodecUtils;
import com.nirvana.xin.codec.l2.CardBoxL2Codec;
import com.nirvana.purist.core.message.CardBox;
import com.nirvana.purist.core.message.DefaultCardBox;
import com.nirvana.purist.core.message.Message;


/**
 * Created by Nirvana on 2018/1/19.
 */
public class MessageEncoder implements Encoder<Message> {

    private CardBoxL2Codec cardBoxL2Codec = CardBoxL2Codec.getInstance();

    private UTF8StringEncoder stringEncoder = UTF8StringEncoder.getInstance();

    private static final MessageEncoder INSTANCE = new MessageEncoder();

    private MessageEncoder() {}

    public static MessageEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] encode(Message object) {
        CardBox cardBox = new DefaultCardBox(object.getPublisherInfo(), object.getContent());
        String content0 = cardBoxL2Codec.encode(cardBox);
        byte[] dateBytes = NumberCodecUtils.getBytes(object.getCreateTime().getTime());
        byte[] contentBytes = stringEncoder.encode(content0);
        byte[] bytes = new byte[dateBytes.length + contentBytes.length];
        System.arraycopy(dateBytes, 0, bytes, 0, dateBytes.length);
        System.arraycopy(dateBytes, 0, bytes, dateBytes.length, contentBytes.length);
        return bytes;
    }

}
