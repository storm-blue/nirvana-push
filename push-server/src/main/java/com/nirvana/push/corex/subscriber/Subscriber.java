package com.nirvana.push.corex.subscriber;


import java.util.Vector;

/**
 * 订阅者
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface Subscriber {

    //收到消息
    void onMessage(Object message);

    Vector<String> getSubTopics();

    void subTopic(String topicName);

    void unSubTopic(String topicName);

    boolean isSubTopic(String topicName);

}
