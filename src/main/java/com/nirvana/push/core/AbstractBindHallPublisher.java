package com.nirvana.push.core;

import java.util.Collection;

public abstract class AbstractBindHallPublisher implements Publisher {

    private MessageHall hall;

    @Override
    public void publish(Message message) {
        hall.putMessage(message);
    }

    @Override
    public void publish(Collection<Message> message) {
        hall.putMessage(message);
    }

    public MessageHall getHall() {
        return hall;
    }

    public void setHall(MessageHall hall) {
        this.hall = hall;
    }
}
