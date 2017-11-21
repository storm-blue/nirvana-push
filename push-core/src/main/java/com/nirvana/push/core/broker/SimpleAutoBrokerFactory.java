package com.nirvana.push.core.broker;

/**
 * Created by Nirvana on 2017/11/19.
 */
public class SimpleAutoBrokerFactory implements BrokerFactory {

    @Override
    public MessageBroker create(String id) {
        return new SimpleAutoMessageBroker(id);
    }

}
