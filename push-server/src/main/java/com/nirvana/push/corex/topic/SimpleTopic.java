package com.nirvana.push.corex.topic;

import com.nirvana.push.corex.session.MapSessionHall;
import com.nirvana.push.corex.session.Session;

import java.util.Vector;

/**
 * topic的简单实现,使用观察者模式
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class SimpleTopic implements Topic {

    private Vector<Long> subs;

    private Long publisher;

    private String name;

    private Long token;

    private MapSessionHall sessionHall = MapSessionHall.getInstance();

    public SimpleTopic(Long publisher, String name) {
        this.publisher = publisher;
        this.name = name;
        subs = new Vector<>();

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getPublisher() {
        return publisher;
    }

    @Override
    public Vector<Long> getSubscriber() {
        return subs;
    }

    @Override
    public void onMessage(Object msg) {

        for (Long sessionId : subs) {
            Session session = sessionHall.getSession(sessionId);
            session.write(msg);
        }

    }

    @Override
    public void addSubscriber(Long sessionId) {

        if (!subs.contains(sessionId)) {
            subs.addElement(sessionId);
        }

    }

    @Override
    public void remvSubscriber(Long sessionId) {
        subs.removeElement(sessionId);
    }

    @Override
    public int countSubscriber() {
        return subs.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }
}
