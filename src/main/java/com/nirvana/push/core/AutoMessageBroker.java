package com.nirvana.push.core;

import java.util.Collection;

/**
 * 当有消息到来，立刻处理消息。
 */
public class AutoMessageBroker extends AbstractMessageBroker {

    @Override
    public void putMessage(Message msg) {
        handle(msg);
    }

    @Override
    public void putMessage(Collection<Message> messages) {
        handle(messages);
    }
}
