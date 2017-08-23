package com.nirvana.push.corex.topic;

import com.nirvana.push.corex.publisher.Publisher;
import com.nirvana.push.corex.subscriber.Subscriber;

import java.util.Vector;

public class Topic implements ITopic {

    private Vector<Subscriber> subs;

    private Publisher publisher;

    private String name;

    private Long token;

    public Topic(Publisher publisher) {
        this.publisher = publisher;
        subs = new Vector<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public Vector<Subscriber> getSubscriber() {
        return subs;
    }

    @Override
    public void onMessage(Object msg) {

    }

    @Override
    public void addSubscriber(Subscriber o) {

        if (o == null)
            throw new NullPointerException();
        if (!subs.contains(o)) {
            subs.addElement(o);
        }

    }

    @Override
    public void remvSubscriber(Subscriber o) {
        subs.removeElement(o);
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
