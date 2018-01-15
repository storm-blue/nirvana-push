package com.nirvana.xin.core.subscriber;

import com.nirvana.xin.core.agent.Agent;
import com.nirvana.xin.core.broker.MessageBroker;
import com.nirvana.xin.core.broker.MessageBrokerContext;
import com.nirvana.xin.core.message.Message;
import com.nirvana.xin.core.message.MessageLevel;
import com.nirvana.xin.core.message.Package;
import com.nirvana.xin.core.message.PackageType;
import com.nirvana.xin.core.session.Session;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Nirvana on 2017/9/7.
 */
public abstract class AgentSubscriber extends AbstractSubscriber implements AcknowledgeSubscriber {

    private static final String SESSION_PACKAGE_SERIAL_NUMBER_KEY = "_session_package_serial_number";

    private MessageBrokerContext brokerSource = MessageBrokerContext.getContext();

    private final Agent agent;

    protected final Session session;

    public AgentSubscriber(Agent agent, Session session) {
        this.agent = agent;
        this.session = session;
        session.setAttribute(SESSION_PACKAGE_SERIAL_NUMBER_KEY, new AtomicLong(0));
    }

    /**
     * Subscribe by topic(broker) name.
     */
    public void subscribe(String brokerName) {
        MessageBroker broker = brokerSource.getBroker(brokerName);
        subscribe(broker);
    }

    /**
     * Unsubscribe by topic(broker) name.
     */
    public void unsubscribe(String brokerName) {
        MessageBroker broker = brokerSource.getBroker(brokerName);
        unsubscribe(broker);
    }

    /**
     * Send message over the agent.
     *
     * @see com.nirvana.xin.core.agent.Agent#sendPackage(Package)
     */
    protected void sendMessage(Message message, MessageLevel level, Object packageId) {
        Package pkg = new Package(PackageType.PUSH_MESSAGE, level, packageId);
        pkg.loadBox(message);
        agent.sendPackage(pkg);
    }

    protected final long obtainSerialNumber() {
        AtomicLong atomicLong = (AtomicLong) session.getAttribute(SESSION_PACKAGE_SERIAL_NUMBER_KEY);
        return atomicLong.incrementAndGet();
    }

    @Override
    public void onAcknowledgement(Object messageId) {

    }
}
