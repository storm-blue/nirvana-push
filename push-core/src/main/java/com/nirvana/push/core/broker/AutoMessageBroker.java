package com.nirvana.push.core.broker;

import java.util.Collection;

/**
 * 当有消息到来，立刻处理消息。
 * Created by Nirvana on 2017/8/3.
 */
public class AutoMessageBroker extends MessageBroker {

    public AutoMessageBroker(Object identifier) {
        super(identifier);
    }

    @Override
    public void putMessage(Object msg) {
        handle(msg);
    }

    @Override
    public void putMessage(Collection<Object> messages) {
        handle(messages);
    }
}
