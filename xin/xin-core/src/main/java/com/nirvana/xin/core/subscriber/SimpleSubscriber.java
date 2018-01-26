package com.nirvana.xin.core.subscriber;

import com.nirvana.xin.core.agent.Agent;
import com.nirvana.purist.core.message.Message;
import com.nirvana.purist.core.message.MessageLevel;
import com.nirvana.xin.core.session.Session;

/**
 * This subscriber send out NO_CONFIRM level message simply.
 * Created by Nirvana on 2017/11/20.
 */
public class SimpleSubscriber extends AgentSubscriber {

    public SimpleSubscriber(Agent agent, Session session) {
        super(agent, session);
    }

    @Override
    public void onMessage(Message message) {
        sendMessage(message, MessageLevel.NO_CONFIRM, obtainSerialNumber());
    }
}
