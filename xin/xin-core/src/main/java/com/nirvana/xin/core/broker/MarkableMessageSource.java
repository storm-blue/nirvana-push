package com.nirvana.xin.core.broker;

import com.nirvana.xin.utils.tuple.Pair;

import java.util.Collection;

/**
 * Created by Nirvana on 2017/11/20.
 */
public interface MarkableMessageSource extends MessageHall {

    /**
     * return the first message that id greater than <code>mark</code>.
     */
    Pair<Long, Object> consumer(long mark);

    /**
     * return the nearest messages that id greater than <code>mark</code>.
     */
    Pair<Long, Collection<Object>> consumer(long mark, int maxNum);

}
