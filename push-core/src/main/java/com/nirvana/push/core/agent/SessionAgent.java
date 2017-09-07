package com.nirvana.push.core.agent;

import com.nirvana.push.core.DestroyFailedException;
import com.nirvana.push.core.agent.exception.ConnectException;

/**
 * SessionAgent.java.
 * Created by Nirvana on 2017/9/6.
 */
public abstract class SessionAgent extends DSTAgent {

    private Session session = new DefaultSession(this);

    @Override
    protected void onConnect(String username, String password) throws ConnectException {
        session.setAttribute("username", username);
    }

    @Override
    protected void doDestroy() throws DestroyFailedException {
        super.doDestroy();
        session.destroy();
    }
}
