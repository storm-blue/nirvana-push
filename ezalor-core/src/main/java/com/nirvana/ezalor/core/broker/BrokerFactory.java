package com.nirvana.ezalor.core.broker;

/**
 * Created by Nirvana on 2017/11/19.
 */
public interface BrokerFactory {

    MessageBroker create(String id);

}
