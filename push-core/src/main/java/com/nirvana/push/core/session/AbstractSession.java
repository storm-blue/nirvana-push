package com.nirvana.push.core.session;

import com.nirvana.push.core.AbstractDestroyable;
import com.nirvana.push.core.DestroyFailedException;

/**
 * Created by Nirvana on 2017/11/16.
 */
public abstract class AbstractSession extends AbstractDestroyable implements Session {

    private final SessionContext sessionContext;

    private final String id;

    public AbstractSession(SessionContext sessionContext, String id) {
        this.sessionContext = sessionContext;
        this.id = id;
    }

    @Override
    protected void doDestroy() throws DestroyFailedException {
        sessionContext.removeSession(this);
    }

    @Override
    public String getId() {
        return id;
    }

    protected SessionContext getSessionContext() {
        return sessionContext;
    }
}
