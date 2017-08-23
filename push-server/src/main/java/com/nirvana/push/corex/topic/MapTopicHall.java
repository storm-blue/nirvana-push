package com.nirvana.push.corex.topic;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapTopicHall implements TopicHall {

    ConcurrentHashMap<String,ITopic> topic = new ConcurrentHashMap<>();

    @Override
    public void addTopic(ITopic topic) {

    }

    @Override
    public void remvTopic(ITopic topic) {

    }

    @Override
    public void remvTopic(String name) {

    }

    @Override
    public Set<ITopic> getAll() {
        return null;
    }
}
