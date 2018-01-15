package com.nirvana.xin.core.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nirvana on 2017/11/16.
 */
public class MemorySessionContext implements SessionContext {

    private static final MemorySessionContext INSTANCE = new MemorySessionContext();

    private static final ConcurrentHashMap<String, Session> context = new ConcurrentHashMap<>();

    //private construction method.
    private MemorySessionContext() {}

    public static MemorySessionContext getInstance() {
        return INSTANCE;
    }

    /**
     * @param session the session to put into the context.
     * @param force   If set to false, this session will not be add to context if there is an previous object with the same id existed in the SessionContext.
     * @return the Session actually in the SessionContext.
     */
    public static Session putSession(Session session, boolean force) {
        if (force) {
            context.put(session.getId(), session);
        } else {
            Session previous = context.putIfAbsent(session.getId(), session);
            if (previous != null) {
                return previous;
            }
        }
        return session;
    }

    @Override
    public Session getSession(String id, boolean create) {
        Session session;
        if (create) {
            session = new DefaultMemorySession(this, id);
            context.put(id, session);
        } else {
            Session previous = context.get(id);
            if (previous != null) {
                return previous;
            }
            session = new DefaultMemorySession(this, id);
            previous = context.putIfAbsent(id, session);
            if (previous != null) {
                return previous;
            }
        }
        return session;
    }

    public void removeSession(String id) {
        context.remove(id);
    }

    public void removeSession(Session session) {
        removeSession(session.getId());
    }

}
