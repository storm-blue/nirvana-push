package com.nirvana.ezalor.core.publisher;

import com.nirvana.ezalor.core.broker.MessageHall;

import java.util.Collection;

/**
 * 绑定单个MessageHall的发布者。
 * Created by Nirvana on 2017/8/3.
 */
public abstract class AbstractBindHallPublisher implements FreePublisher {

    private MessageHall hall;

    public AbstractBindHallPublisher() {
    }

    public AbstractBindHallPublisher(MessageHall hall) {
        this.hall = hall;
    }

    @Override
    public void publish(Object message) {
        hall.putMessage(message);
    }

    @Override
    public void publish(Collection<Object> messages) {
        hall.putMessage(messages);
    }

    public MessageHall getHall() {
        return hall;
    }

    public void setHall(MessageHall hall) {
        this.hall = hall;
    }
}
