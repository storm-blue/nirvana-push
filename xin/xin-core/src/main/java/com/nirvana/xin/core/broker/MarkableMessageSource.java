package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.broker.MessageHall;
import com.nirvana.purist.utils.tuple.Pair;

import java.util.Collection;

/**
 * Created by Nirvana on 2017/11/20.
 */
public interface MarkableMessageSource extends MessageHall {

    /**
     * return the first message that id greater than <code>mark</code>.
     *
     * @return a pair of the message source current mark number and the message.
     */
    Pair<Long, Object> consumer(long mark);

    /**
     * return the nearest messages that id greater than <code>mark</code>.
     *
     * @return a pair of the message source current mark number and the message collection.
     */
    Pair<Long, Collection<Object>> consumer(long mark, int maxNum);

}
