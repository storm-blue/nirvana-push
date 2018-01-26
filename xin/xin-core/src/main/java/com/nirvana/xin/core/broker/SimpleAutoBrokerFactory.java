package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.broker.MessageBroker;

/**
 * Created by Nirvana on 2017/11/19.
 */
public class SimpleAutoBrokerFactory implements BrokerFactory {

    @Override
    public MessageBroker create(String id) {
        return new SimpleAutoMessageBroker(id);
    }

}
