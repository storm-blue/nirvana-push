package com.nirvana.push.core.user;

import com.nirvana.push.core.AbstractMultiHallPublisher;
import com.nirvana.push.core.Message;
import com.nirvana.push.core.Subscriber;

/**
 * com.nirvana.push.core.user.AbstractAgent.java.
 * <p>
 * Created by Nirvana on 2017/8/7.
 */
public abstract class AbstractAgent extends AbstractMultiHallPublisher implements Subscriber, Agent {

    @Override
    public void onMessage(Message msg) {
    }
}
