package com.nirvana.ezalor.core.agent;

import com.nirvana.ezalor.core.DestroyFailedException;
import com.nirvana.ezalor.core.agent.exception.ConnectionException;
import com.nirvana.ezalor.core.message.MessageLevel;
import com.nirvana.ezalor.core.publisher.DefaultNamePublisher;
import com.nirvana.ezalor.core.publisher.NamePublisher;
import com.nirvana.ezalor.core.session.MemorySessionContext;
import com.nirvana.ezalor.core.session.Session;
import com.nirvana.ezalor.core.session.SessionContext;
import com.nirvana.ezalor.core.subscriber.AgentSubscriber;
import com.nirvana.ezalor.core.subscriber.SimpleSubscriber;
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
        publisher.publish(topicName, message);
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
