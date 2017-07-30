package com.nirvana.push.core;

import java.util.Collection;

/**
 * 消息发布者。
 */
public interface Publisher {

    void publish(Message msg);

    void publish(Collection<Message> msg);

}
