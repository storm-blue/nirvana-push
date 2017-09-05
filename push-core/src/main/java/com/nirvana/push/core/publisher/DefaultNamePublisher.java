package com.nirvana.push.core.publisher;

import com.nirvana.push.core.broker.MessageBroker;
import com.nirvana.push.core.broker.MessageBrokerSource;

import java.util.Collection;

/**
 * 简单写入到默认MessageBroker的发布者。
 * Created by Nirvana on 2017/8/17.
 */
public class DefaultNamePublisher<T> implements NamePublisher<T> {

    private MessageBrokerSource brokerSource = MessageBrokerSource.getSource();

    public DefaultNamePublisher() {
    }

    @Override
    public void publish(String name, T msg) {
        MessageBroker broker = brokerSource.create(name);
        broker.putMessage(msg);
    }

    @Override
    public void publish(String name, Collection<T> msg) {
        MessageBroker broker = brokerSource.create(name);
        for (T m : msg) {
            broker.putMessage(m);
        }
    }
}
