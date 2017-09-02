package com.nirvana.push.core.publisher;

import com.nirvana.push.core.broker.MessageHall;

import java.util.Collection;

/**
 * 消息发布者。
 * Created by Nirvana on 2017/8/3.
 */
public interface Publisher<T> {

    void publish(T msg);

    void publish(Collection<T> msg);

}
