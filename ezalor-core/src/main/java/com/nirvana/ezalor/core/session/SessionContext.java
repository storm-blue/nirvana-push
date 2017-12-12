package com.nirvana.ezalor.core.session;


/**
 * Created by Nirvana on 2017/11/16.
 */
public interface SessionContext {

    /**
     * @param id     the session's id.
     * @param create If set to true, SessionContext will always create a new Session Object.
     *               If set to false, the SessionContext will check if it is existed in context first. If not, create new One.
     */
    Session getSession(String id, boolean create);

    /**
     * Remove the session identified by this id from context.
     */
    void removeSession(String id);

    /**
     * Remove the session from context.
     */
    void removeSession(Session session);

}
