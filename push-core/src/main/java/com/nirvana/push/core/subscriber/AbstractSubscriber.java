package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.AbstractDestroyable;
import com.nirvana.push.core.DestroyFailedException;
import com.nirvana.push.core.broker.MessageBroker;

import java.util.HashSet;
import java.util.Set;

/**
 * 主动订阅主题的订阅者。
 * Created by Nirvana on 2017/9/7.
 */
public abstract class AbstractSubscriber extends AbstractDestroyable implements Subscriber {

    /**
     * 订阅的所有主题。
     * 此结构是否需要做线程安全？
     */
    protected Set<MessageBroker> brokers = new HashSet<>();

    /**
     * 订阅一个主题。
     */
    public void subscribe(MessageBroker broker) {
        brokers.add(broker);
        broker.addSubscriber(this);
    }

    /**
     * 取消订阅一个主题。此时移除MessageBroker和Subscriber的双向关联。
     */
    public void unsubscribe(MessageBroker broker) {
        brokers.remove(broker);
        broker.removeSubscriber(this);
    }

    /**
     * 销毁时，取消订阅所有主题。
     */
    protected void doDestroy() throws DestroyFailedException {
        for (MessageBroker broker : brokers) {
            unsubscribe(broker);
        }
    }
}
