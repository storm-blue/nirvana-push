package com.nirvana.ezalor.core.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Default session implement.
 * Created by Nirvana on 2017/9/6.
 */
public class DefaultMemorySession extends AbstractSession {

    private long createTime = System.currentTimeMillis();

    private long lastTime = System.currentTimeMillis();

    private Map<String, Object> attributes = new HashMap<>();

    DefaultMemorySession(MemorySessionContext sessionContext, String id) {
        super(sessionContext, id);
    }

    @Override
    public long getCreationTime() {
        return createTime;
    }

    @Override
    public long getLastAccessedTime() {
        return lastTime;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
        public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
    }

}
