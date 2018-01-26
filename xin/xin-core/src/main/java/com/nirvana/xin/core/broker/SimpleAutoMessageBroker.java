package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.broker.MessageBroker;
import com.nirvana.purist.core.message.Message;
import com.nirvana.purist.core.subscriber.Subscriber;

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
    public void putMessage(Message message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onMessage(message);
        }
    }

    @Override
    public void putMessage(Collection<Message> messages) {
        for (Message message : messages) {
            putMessage(message);
        }
    }
}