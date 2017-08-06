package com.nirvana.push.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * topic代理人。负责维持
 * Publisher/Subscriber/Topic之间的关系。
 * Created by Nirvana on 2017/8/3.
 */
public abstract class AbstractMessageBroker implements MessageHall {

    private Set<Subscriber> subscribers = new HashSet<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    protected void handle(Message message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onMessage(message);
        }
    }

    protected void handle(Collection<Message> messages) {
        for (Message message : messages) {
            handle(message);
        }
    }

}
