package com.nirvana.push.core.broker;

import java.util.Collection;

/**
 * A top interface that can put message in.
 * Created by Nirvana on 2017/8/3.
 */
public interface MessageHall {

    void putMessage(Object message);

    void putMessage(Collection<Object> messages);

}
