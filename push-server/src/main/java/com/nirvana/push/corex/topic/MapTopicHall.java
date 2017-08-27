package com.nirvana.push.corex.topic;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 *  Map实现简单的topic存储
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class MapTopicHall implements TopicHall {

    private MapTopicHall(){

    }

    private static TopicHall topicHall = new MapTopicHall();

    public static TopicHall getInstance(){
        return topicHall;
    }

    private ConcurrentHashMap<String, Topic> topicMap = new ConcurrentHashMap<>();

    @Override
    public void addTopic(Topic topic) {
        topicMap.put(topic.getName(), topic);
    }


    @Override
    public void remvTopic(String name) {
        topicMap.remove(name);
    }


    @Override
    public Set<Topic> getAll() {
        return null;
    }

    @Override
    public Topic getTopic(String name) {
        return topicMap.get(name);
    }

    @Override
    public boolean contains(String name) {
        return topicMap.containsKey(name);
    }

    @Override
    public int count() {
        return topicMap.size();
    }


}
