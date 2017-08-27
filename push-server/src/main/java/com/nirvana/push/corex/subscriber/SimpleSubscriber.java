package com.nirvana.push.corex.subscriber;

import com.nirvana.push.corex.session.Session;

public class SimpleSubscriber implements Subscriber {

    private Session session;

    public SimpleSubscriber(Session session) {
        this.session = session;
    }


    @Override
    public void onMessage(Object message) {
        session.write(message);
    }
}
