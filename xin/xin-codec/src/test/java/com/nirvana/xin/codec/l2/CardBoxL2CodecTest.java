package com.nirvana.xin.codec.l2;

import com.nirvana.purist.core.message.*;
import com.nirvana.purist.core.message.Package;
import org.junit.Test;


/**
 * Created by Nirvana on 2018/1/16.
 */
public class CardBoxL2CodecTest {

    private CardBoxL2Codec cardBoxL2Codec = CardBoxL2Codec.getInstance();

    private static final String CONTENT = "-[x~01-content~01\nx~12-content~12\n]\n-[x~01-content~01\nx~12-content~12\n]\n";

    @Test
    public void decode() {
        CardBox box = cardBoxL2Codec.decode(CONTENT);
        for (Card card : box) {
            System.out.println("key:" + card.getName());
            System.out.println("value:" + card.getContent());
            System.out.println("#####");
        }
    }

    @Test
    public void encode() {
        CardBox cardBox = new DefaultCardBox();

        cardBox.addCard(new SimpleCard("key1-", "-value1"));
        cardBox.addCard(new SimpleCard("key2~", "~value2"));
        cardBox.addCard(new SimpleCard("key3#", "#value3"));

        CardBox innerCardBox = new DefaultCardBox();
        innerCardBox.addCard(new SimpleCard("key1-", "-value1"));
        innerCardBox.addCard(new SimpleCard("key2~", "~value2"));

        cardBox.addCard(new SimpleCard(innerCardBox));

        System.out.println(cardBoxL2Codec.encode(cardBox));
    }
}