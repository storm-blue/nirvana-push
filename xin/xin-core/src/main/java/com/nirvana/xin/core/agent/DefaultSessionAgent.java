package com.nirvana.xin.core.agent;

import com.nirvana.purist.core.DestroyFailedException;
import com.nirvana.xin.core.agent.exception.ConnectionException;
import com.nirvana.purist.core.message.*;
import com.nirvana.xin.core.publisher.DefaultNamePublisher;
import com.nirvana.xin.core.publisher.NamePublisher;
import com.nirvana.xin.core.session.MemorySessionContext;
import com.nirvana.xin.core.session.Session;
import com.nirvana.xin.core.session.SessionContext;
import com.nirvana.xin.core.subscriber.AgentSubscriber;
import com.nirvana.xin.core.subscriber.SimpleSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/11/16.
 */
public class DefaultSessionAgent extends AbstractCommunicationAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSessionAgent.class);

    private Session session;

    private boolean destroySessionOnDisconnect = true;

    private boolean alwaysCreateNewSession = false;

    private SessionContext sessionContext = MemorySessionContext.getInstance();

    private AgentSubscriber subscriber = null;

    private NamePublisher publisher = new DefaultNamePublisher();

    public DefaultSessionAgent(PackageDispatcher exchanger) {
        super(exchanger);
    }

    @Override
    protected void onConnect(String username, String password) throws ConnectionException {
        LOGGER.debug("on connect, username: {}, password: {}", username, password);
        session = sessionContext.getSession(username, alwaysCreateNewSession);
        subscriber = new SimpleSubscriber(this, session);
    }

    @Override
    protected void onSubscribe(String topicName) {
        subscriber.subscribe(topicName);
    }

    @Override
    protected void onPushMessageAck(Object messageId) {
        subscriber.onAcknowledgement(messageId);
    }

    @Override
    protected void onExactlyOnceMessageAck(Object messageId) {
        subscriber.onAcknowledgement(messageId);
    }

    @Override
    protected void onUnsubscribe(String topicName) {
        subscriber.unsubscribe(topicName);
    }

    @Override
    protected void onPublish(String topicName, String message, MessageLevel level) {
        LOGGER.debug("on publish, topicName: {}, message: {}, level: {}", topicName, message, level);

        CardBox publisherInfo = new DefaultCardBox();//TODO add publisher info.
        CardBox content = new DefaultCardBox(message);
        Message msg = new Message(publisherInfo, content);
        publisher.publish(topicName, msg);
    }

    private static final String LAST_PING = "_last_ping";

    @Override
    protected void onPing() {
        session.setAttribute(LAST_PING, System.currentTimeMillis());
    }

    @Override
    protected void onDisconnect() {
        try {
            doDestroy();
        } catch (DestroyFailedException e) {
            LOGGER.error("agent destroy error: ", e);
        }
        disconnect();
    }

    @Override
    protected void doDestroy() throws DestroyFailedException {
        if (destroySessionOnDisconnect && session != null) {
            session.destroy();
        }
        if (subscriber != null) {
            subscriber.destroy();
        }
    }

    public boolean isDestroySessionOnDisconnect() {
        return destroySessionOnDisconnect;
    }

    public void setDestroySessionOnDisconnect(boolean destroySessionOnDisconnect) {
        this.destroySessionOnDisconnect = destroySessionOnDisconnect;
    }

    public boolean isAlwaysCreateNewSession() {
        return alwaysCreateNewSession;
    }

    public void setAlwaysCreateNewSession(boolean alwaysCreateNewSession) {
        this.alwaysCreateNewSession = alwaysCreateNewSession;
    }
}
