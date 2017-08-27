package com.nirvana.push.corex.subscriber;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 保存订阅者
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class SubscriberStore {

    private static SubscriberStore subscriberStore = new SubscriberStore();


    public static SubscriberStore getInstance() {
        return subscriberStore;
    }

    private ConcurrentMap<Long, Subscriber> persistentSubscriptionStore = new ConcurrentHashMap<>();


    private SubscriberStore() {

    }


    public void addSubscriber(Long sessionId, Subscriber subscriber) {
        persistentSubscriptionStore.put(sessionId, subscriber);
    }


    public Subscriber getSubscriber(Long sessionId) {
        return persistentSubscriptionStore.get(sessionId);
    }

    public Subscriber getSubscriberEnhance(Long sessionId) {

        Subscriber subscriber = persistentSubscriptionStore.get(sessionId);

        if (subscriber == null) {
            subscriber = new SimpleSubscriber(sessionId);
            this.addSubscriber(sessionId, subscriber);

            return subscriber;

        } else {
            return subscriber;
        }


    }

    public boolean constanse(Long sessionId) {
        return persistentSubscriptionStore.keySet().contains(sessionId);
    }
}
