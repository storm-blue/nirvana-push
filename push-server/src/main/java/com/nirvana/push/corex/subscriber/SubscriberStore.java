package com.nirvana.push.corex.subscriber;

import com.nirvana.push.corex.topic.ITopic;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 保存订阅者与topic的关系
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class SubscriberStore {

    private static SubscriberStore subscriberStore =new SubscriberStore();


    public static SubscriberStore getInstance() {
        return subscriberStore;
    }

    //sub - > List<Topic>
    private ConcurrentMap<Long, Set<ITopic>> persistentSubscriptionStore = new ConcurrentHashMap<>();


    private SubscriberStore() {

    }


    public void addTopicForSub(Long clientId, ITopic topic) {

        if (persistentSubscriptionStore.containsKey(clientId)) {

            Set<ITopic> topics = persistentSubscriptionStore.get(clientId);

            topics.add(topic);
        } else {

            Set<ITopic> topics = new HashSet<>();
            topics.add(topic);

            persistentSubscriptionStore.put(clientId, topics);
        }

    }


    public Set<ITopic> getTopicsBySub(Long clientId) {
        return persistentSubscriptionStore.get(clientId);
    }

}
