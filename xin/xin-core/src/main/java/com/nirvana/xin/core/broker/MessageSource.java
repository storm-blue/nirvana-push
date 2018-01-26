package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.broker.MessageHall;
import com.nirvana.purist.core.message.Message;

import java.util.Collection;

/**
 * Created by Nirvana on 2018/1/18.
 */
public interface MessageSource extends MessageHall {

    Collection<Message> getAfter(long index);

    Collection<Message> getBetween(long index1, long index2);

    Message get(long index);

}
