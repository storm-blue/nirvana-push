package com.nirvana.push.core.publisher;

import com.nirvana.push.core.broker.MessageBroker;
import com.nirvana.push.core.broker.MessageBrokerContext;

import java.util.Collection;

/**
 * 简单写入到默认MessageBroker的发布者。
 * Created by Nirvana on 2017/8/17.
 */
public class DefaultNamePublisher implements NamePublisher {

    private MessageBrokerContext brokerContext = MessageBrokerContext.getContext();

    public DefaultNamePublisher() {
    }

    @Override
    public void publish(String name, Object message) {
        MessageBroker broker = brokerContext.getBroker(name);
        broker.putMessage(message);
    }

    @Override
    public void publish(String name, Collection<Object> messages) {
        MessageBroker broker = brokerContext.getBroker(name);
        for (Object message : messages) {
            broker.putMessage(message);
        }
    }
}
