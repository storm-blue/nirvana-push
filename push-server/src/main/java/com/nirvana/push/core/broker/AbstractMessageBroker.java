package com.nirvana.push.core.broker;

import com.nirvana.push.core.subscriber.Subscriber;

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

    @SuppressWarnings("unchecked")
    protected void handle(Object message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onMessage(message);
        }
    }

    protected void handle(Collection<Object> messages) {
        for (Object message : messages) {
            handle(message);
        }
    }

}
