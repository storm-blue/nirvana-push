package com.nirvana.push.core.broker;

import com.nirvana.push.core.subscriber.Subscriber;
import com.nirvana.push.utils.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * topic代理人。负责维持
 * Publisher/Subscriber/Topic之间的关系。
 * Created by Nirvana on 2017/8/3.
 */
public abstract class MessageBroker implements MessageHall {

    /**
     * 标识符一旦创建不可更改。
     */
    private final Object identifier;

    /**
     * 线程安全的订阅者集合。
     */
    private Set<Subscriber> subscribers = new ConcurrentHashMap<Subscriber, Boolean>().keySet(true);

    public MessageBroker(Object identifier) {
        Assert.notNull(identifier, "标识符不能为空");
        this.identifier = identifier;
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
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

    public Object getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageBroker that = (MessageBroker) o;

        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
