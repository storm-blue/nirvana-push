package com.nirvana.push.corex.publisher;

import com.nirvana.push.corex.topic.Topic;
import com.nirvana.push.corex.topic.MapTopicHall;
import com.nirvana.push.corex.topic.SimpleTopic;
import com.nirvana.push.corex.topic.TopicHall;

/**
 * 发布者实现
 */
public class SimplePublisher implements Publisher {

    private TopicHall topicHall = MapTopicHall.getInstance();

    private Long sessionId;

    public SimplePublisher(Long sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public void publish(String topicName) {
        Topic topic = new SimpleTopic(sessionId, topicName);
        topicHall.addTopic(topic);
    }

    @Override
    public void pushMessage(String topicName, Object msg) {
        topicHall.getTopic(topicName).onMessage(msg);
    }
}
