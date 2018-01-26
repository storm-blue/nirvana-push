package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.broker.MessageBroker;

/**
 * Created by Nirvana on 2017/11/19.
 */
public interface BrokerFactory {

    MessageBroker create(String id);

}
