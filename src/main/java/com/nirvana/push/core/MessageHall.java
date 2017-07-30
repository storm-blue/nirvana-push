package com.nirvana.push.core;

import java.util.Collection;

public interface MessageHall {

    void putMessage(Message message);

    void putMessage(Collection<Message> messages);

}
