package com.nirvana.ezalor.core.broker;

import com.nirvana.ezalor.core.subscriber.Subscriber;
import com.nirvana.ezalor.utils.Assert;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * topic代理人。负责维持
 * Publisher/Subscriber/Topic之间的关系。
 * Created by Nirvana on 2017/8/3.
 */
public abstract class MessageBroker implements MessageHall {

    /**
     * After construction, Id can not be edit.
     */
    private final String id;

    /**
     * Thread safe set from ConcurrentHashMap.
     */
    protected final Set<Subscriber> subscribers = new ConcurrentHashMap<Subscriber, Boolean>().keySet(true);

    public MessageBroker(String id) {
        Assert.notNull(id, "broker id must not be null.");
        this.id = id;
    }

    public void addSubscriber(Subscriber subscriber) {
        beforeAdd(subscriber);
        subscribers.add(subscriber);
        afterAdd(subscriber);
    }

    //Do something before adding a subscriber.
    protected void beforeAdd(Subscriber subscriber) {}

    //Do something after adding a subscriber.
    protected void afterAdd(Subscriber subscriber) {}

    public void removeSubscriber(Subscriber subscriber) {
        beforeRemove(subscriber);
        subscribers.remove(subscriber);
        afterRemove(subscriber);
    }

    //Do something before removing a subscriber.
    protected void beforeRemove(Subscriber subscriber) {}

    //Do something after removing a subscriber.
    protected void afterRemove(Subscriber subscriber) {}

    public String getId() {
        return id;
    }

    public abstract void work();
}
