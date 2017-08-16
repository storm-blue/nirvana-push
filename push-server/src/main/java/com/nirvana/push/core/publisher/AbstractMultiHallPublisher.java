package com.nirvana.push.core.publisher;

import com.nirvana.push.core.broker.MessageHall;

import java.util.Collection;

/**
 * 绑定多个MessageHall的发布者。
 * Created by Nirvana on 2017/8/3.
 */
public class AbstractMultiHallPublisher<T> implements Publisher<T> {

    private Collection<MessageHall> halls;

    @Override
    public void publish(T msg) {
        for (MessageHall hall : halls) {
            hall.putMessage(msg);
        }
    }

    @Override
    public void publish(Collection<T> msg) {

    }

    public Collection<MessageHall> getHalls() {
        return halls;
    }

    public void setHalls(Collection<MessageHall> halls) {
        this.halls = halls;
    }
}
