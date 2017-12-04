package com.nirvana.push.core.broker;

import com.nirvana.push.utils.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
