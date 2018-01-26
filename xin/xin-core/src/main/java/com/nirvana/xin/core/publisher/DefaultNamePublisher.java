package com.nirvana.xin.core.publisher;

import com.nirvana.purist.core.broker.MessageBroker;
import com.nirvana.xin.core.broker.MessageBrokerContext;
import com.nirvana.purist.core.message.Message;

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
    public void publish(String name, Message message) {
        MessageBroker broker = brokerContext.getBroker(name);
        message.grant(broker);
        broker.putMessage(message);
    }

    @Override
    public void publish(String name, Collection<Message> messages) {
        MessageBroker broker = brokerContext.getBroker(name);
        for (Message message : messages) {
            broker.putMessage(message);
        }
    }
}
