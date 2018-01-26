package com.nirvana.xin.codec.decoder;

import com.nirvana.xin.codec.ProtocolException;
import com.nirvana.xin.codec.l2.CardBoxL2Codec;
import com.nirvana.purist.core.message.CardBox;
import com.nirvana.purist.core.message.Message;

import java.util.Date;

/**
 * Created by Nirvana on 2018/1/23.
 */
public class MessageDecoder extends AbstractDecoder<Message> {

    private CardBoxL2Codec cardBoxL2Codec = CardBoxL2Codec.getInstance();

    private UTF8StringDecoder utf8StringDecoder = UTF8StringDecoder.getInstance();

    private static final MessageDecoder INSTANCE = new MessageDecoder();

    private MessageDecoder() {}

    public static MessageDecoder getInstance() {
        return INSTANCE;
    }

    @Override
    public Message decode(byte[] bytes, int pos, int length) {
        if (pos + length >= bytes.length) {
            throw new ProtocolException("not enough bytes.");
        }
        if (length < 8) {
            throw new ProtocolException("parse message needs at least 8 bytes.");
        }

        long datetime = (long) (bytes[pos] & 0xff)
                | ((long) (bytes[pos + 1] & 0xff) << 8)
                | ((long) (bytes[pos + 2] & 0xff) << 16)
                | ((long) (bytes[pos + 3] & 0xff) << 24)
                | ((long) (bytes[pos + 4] & 0xff) << 32)
                | ((long) (bytes[pos + 5] & 0xff) << 40)
                | ((long) (bytes[pos + 6] & 0xff) << 48)
                | ((long) (bytes[pos + 7] & 0xff) << 56);
        Date date = new Date(datetime);

        String l2String = utf8StringDecoder.decode(bytes, pos + 8, length - 8);
        CardBox cardBox = cardBoxL2Codec.decode(l2String);
        Object publisherInfo0 = cardBox.getCard(0).getContent();
        Object content0 = cardBox.getCard(1).getContent();
        if (publisherInfo0 == null) {
            throw new ProtocolException("message decode error: publishInfo part not found.");
        }
        if (content0 == null) {
            throw new ProtocolException("message decode error: content part not found.");
        }
        if (!(publisherInfo0 instanceof CardBox)) {
            throw new ProtocolException("message decode error: publishInfo part error: " + publisherInfo0);
        }
        if (!(content0 instanceof CardBox)) {
            throw new ProtocolException("message decode error: content part error: " + content0);
        }

        return new Message(date, (CardBox) publisherInfo0, (CardBox) content0);
    }

}
