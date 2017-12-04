package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.agent.Agent;
import com.nirvana.push.core.message.Message;
import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.session.Session;

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
