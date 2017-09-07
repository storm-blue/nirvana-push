package com.nirvana.push.core.agent;

import com.nirvana.push.core.DestroyFailedException;
import com.nirvana.push.core.Destroyable;
import com.nirvana.push.core.agent.exception.ConnectException;
import com.nirvana.push.core.subscriber.DSTSubscriber;

/**
 * SessionAgent.java.
 * Created by Nirvana on 2017/9/6.
 */
public abstract class SessionAgent extends DSTAgent implements Destroyable {

    private Session session = new DefaultSession(this);

    private DSTSubscriber subscriber = new DSTSubscriber(this);

    private volatile DestroyStatus destroyStatus = DestroyStatus.NOT_DESTROY;

    @Override
    protected void onConnect(String username, String password) throws ConnectException {

    }

    @Override
    protected void onSubscribe(String topicName) {

    }

    @Override
    protected void onPushMessageAck() {

    }

    @Override
    protected void onExactlyOnceMessageAck() {

    }

    @Override
    protected void onUnsubscribe(String topicName) {

    }

    @Override
    protected void onPublish(String topicName, String message) {

    }

    @Override
    protected void onPing() {

    }

    @Override
    protected void onDisconnect() {

    }

    @Override
    public void destroy() throws DestroyFailedException {
        destroyStatus = DestroyStatus.DESTROYING;
        if (session.destroyStatus() != DestroyStatus.NOT_DESTROY) {
            session.destroy();
        }
        if (subscriber.destroyStatus() != DestroyStatus.NOT_DESTROY) {
            session.destroy();
        }
        disconnect();
        destroyStatus = DestroyStatus.DESTROYED;
    }

    @Override
    public DestroyStatus destroyStatus() {
        return destroyStatus;
    }
}
