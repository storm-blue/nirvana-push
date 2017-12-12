package com.nirvana.ezalor.core.publisher;

import com.nirvana.ezalor.core.broker.MessageHall;

import java.util.Collection;

/**
 * 绑定多个MessageHall的发布者。
 * Created by Nirvana on 2017/8/3.
 */
public class AbstractMultiHallPublisher implements FreePublisher {

    private Collection<MessageHall> halls;

    @Override
    public void publish(Object message) {
        for (MessageHall hall : halls) {
            hall.putMessage(message);
        }
    }

    @Override
    public void publish(Collection<Object> messages) {

    }

    public Collection<MessageHall> getHalls() {
        return halls;
    }

    public void setHalls(Collection<MessageHall> halls) {
        this.halls = halls;
    }
}
