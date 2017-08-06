package com.nirvana.push.core;

import java.util.Collection;

/**
 * 绑定多个MessageHall的发布者。
 * Created by Nirvana on 2017/8/3.
 */
public class AbstractMultiHallPublisher implements Publisher {

    private Collection<MessageHall> halls;

    @Override
    public void publish(Message msg) {
        for (MessageHall hall : halls) {
            hall.putMessage(msg);
        }
    }

    @Override
    public void publish(Collection<Message> msg) {

    }

    public Collection<MessageHall> getHalls() {
        return halls;
    }

    public void setHalls(Collection<MessageHall> halls) {
        this.halls = halls;
    }
}
