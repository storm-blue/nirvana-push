package com.nirvana.push.corex.subscriber;

import com.nirvana.push.corex.session.MapSessionHall;
import com.nirvana.push.corex.session.Session;
import com.nirvana.push.corex.topic.MapTopicHall;
import com.nirvana.push.corex.topic.TopicHall;

import java.util.Vector;

/**
 * 普通订阅者
 */
public class SimpleSubscriber implements Subscriber {

    private Long sessionId;

    private MapSessionHall sessionHall = MapSessionHall.getInstance();

    private Vector<String> topics = new Vector<>();

    private TopicHall topicHall = MapTopicHall.getInstance();

    public SimpleSubscriber(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void onMessage(Object message) {
        Session session = sessionHall.getSession(sessionId);
        if (session != null) {
            if (session.isConnected()) {
                session.write(message);
            }
        }
    }

    @Override
    public Vector<String> getSubTopics() {
        return topics;
    }

    @Override
    public void subTopic(String topicName) {
        topicHall.getTopic(topicName).addSubscriber(sessionId);
        topics.add(topicName);
    }

    @Override
    public void unSubTopic(String topicName) {
        topicHall.getTopic(topicName).remvSubscriber(sessionId);
        topics.remove(topicName);
    }

    @Override
    public boolean isSubTopic(String topicName) {
        return topics.contains(topicName);
    }
}
