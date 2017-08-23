package com.nirvana.push.corex.topic;

import com.nirvana.push.corex.publisher.Publisher;
import com.nirvana.push.corex.subscriber.Subscriber;

import java.util.Vector;


/**
 * topic
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface ITopic {

    String getName();

    //获取该topic的发布者
    Publisher getPublisher();

    //获取所有topic的订阅者
    Vector<Subscriber> getSubscriber();

    //push消息
    void onMessage(Object msg);

    //添加订阅者
    void addSubscriber(Subscriber o);

    //删除订阅者
    void remvSubscriber(Subscriber o);

    //订阅者数量
    int countSubscriber();

}
