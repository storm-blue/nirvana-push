package com.nirvana.push.core.message;

import com.nirvana.push.core.broker.MessageBroker;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Nirvana on 2017/11/16.
 */
public class Message implements CardBox {

    private MessageMeta meta;

    private CardBox cardBox;

    public Message(MessageBroker broker) {
        meta = new MessageMeta();
        meta.setBroker(broker);
        cardBox = new DefaultCardBox();
    }

    public Message(MessageMeta meta) {
        this.meta = meta;
        cardBox = new DefaultCardBox();
    }

    public Message(MessageMeta meta, CardBox cardBox) {
        this.meta = meta;
        this.cardBox = cardBox;
    }

    public MessageMeta getMeta() {
        return meta;
    }

    public Object getId() {
        return meta.getId();
    }

    public MessageBroker getBroker() {
        return meta.getBroker();
    }

    public MessageLevel getExpectMessageLevel() {
        return meta.getExpectMessageLevel();
    }

    @Override
    public void addCard(Card card) {
        cardBox.addCard(card);
    }

    @Override
    public Card getCard(int index) {
        return cardBox.getCard(index);
    }

    @Override
    public Card getCard(String name) {
        return cardBox.getCard(name);
    }

    @Override
    public int size() {
        return cardBox.size();
    }

    @Override
    public Collection<String> names() {
        return cardBox.names();
    }

    @Override
    public Iterator<Card> iterator() {
        return cardBox.iterator();
    }
}