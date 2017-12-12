package com.nirvana.ezalor.core.broker;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的Broker管理器。
 * Created by Nirvana on 2017/9/3.
 */
public class MessageBrokerContext {

    private static final MessageBrokerContext instance = new MessageBrokerContext();

    private BrokerFactory brokerFactory = new SimpleAutoBrokerFactory();

    private ConcurrentHashMap<String, MessageBroker> brokers = new ConcurrentHashMap<>();

    private MessageBrokerContext() {
    }

    public static MessageBrokerContext getContext() {
        return instance;
    }

    /**
     * Get a broker from BrokerSource by name.
     *
     * @param name broker name
     */
    public MessageBroker getBroker(String name) {
        MessageBroker broker = brokers.get(name);
        if (broker == null) {
            broker = brokerFactory.create(name);
            MessageBroker previous = brokers.putIfAbsent(name, broker);
            if (previous != null) {
                broker = previous;
            }
        }
        return broker;
    }

    Iterator<MessageBroker> interator() {

        return new Iterator<MessageBroker>() {

            private Iterator<String> iterator = brokers.keySet().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public MessageBroker next() {
                return brokers.get(iterator.next());
            }
        };

    }
}
