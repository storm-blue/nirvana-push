package com.nirvana.ezalor.core.broker;

import com.nirvana.ezalor.core.message.Message;
import com.nirvana.ezalor.core.message.SimpleCard;
import com.nirvana.ezalor.core.subscriber.Subscriber;

import java.util.Collection;

/**
 * A simple broker implement.
 * The messages this broker received will be pass to subscribers immediately.
 * Created by Nirvana on 2017/11/16.
 */
public class SimpleAutoMessageBroker extends MessageBroker {

    public SimpleAutoMessageBroker(String id) {
        super(id);
    }

    @Override
    public void work() {
        throw new UnsupportedOperationException("SimpleAutoMessageBroker can not manually work.");
    }

    @Override
    public void putMessage(Object message) {
        for (Subscriber subscriber : subscribers) {
            Message message0 = new Message(this);
            message0.addCard(new SimpleCard(message));
            subscriber.onMessage(message0);
        }
    }

    @Override
    public void putMessage(Collection<Object> messages) {
        for (Object message : messages) {
            putMessage(message);
        }
    }
}