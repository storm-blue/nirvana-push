package com.nirvana.purist.core.broker;

import com.nirvana.purist.core.message.Message;

import java.util.Collection;

/**
 * A top interface that can put message in.
 * Created by Nirvana on 2017/8/3.
 */
public interface MessageHall {

    void putMessage(Message message);

    void putMessage(Collection<Message> messages);

}
