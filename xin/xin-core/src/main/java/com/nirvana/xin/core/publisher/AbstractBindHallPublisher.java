package com.nirvana.xin.core.publisher;

import com.nirvana.purist.core.broker.MessageHall;
import com.nirvana.purist.core.message.Message;

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
    public void publish(Message message) {
        hall.putMessage(message);
    }

    @Override
    public void publish(Collection<Message> messages) {
        hall.putMessage(messages);
    }

    public MessageHall getHall() {
        return hall;
    }

    public void setHall(MessageHall hall) {
        this.hall = hall;
    }
}
