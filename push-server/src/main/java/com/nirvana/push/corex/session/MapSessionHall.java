package com.nirvana.push.corex.session;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  session管理
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class MapSessionHall {

    /**
     * 在线会话
     */
    private final ConcurrentHashMap<Long, Session> onlineSessions = new ConcurrentHashMap<>();

    /**
     * 加入
     * @param sessionId
     * @param session
     * @return
     */
    public boolean putSession(long sessionId, Session session){
        if(!onlineSessions.containsKey(sessionId)){
            boolean success = onlineSessions.putIfAbsent(sessionId, session)== null? true : false;
            return success;
        }
        return false;
    }

    /**
     * 移除
     * @param sessionId
     */
    public Session remvSession(long sessionId){

        if(onlineSessions.containsKey(sessionId)){
            return onlineSessions.remove(sessionId);
        }
         return null;
    }


    /**
     * 是否在线
     * @param sessionId
     * @return
     */
    public boolean isOnline(long sessionId){
        return onlineSessions.containsKey(sessionId);
    }


    /**
     * 获取session
     * @param sessionId
     * @return
     */
    public Session getSession(long sessionId){
        return onlineSessions.get(sessionId);
    }

    /**
     * 获取所有session
     * @return
     */
    public Set<Long> getOnlines() {
        return Collections.unmodifiableSet(onlineSessions.keySet());
    }
}
