package com.nirvana.push.corex;

import com.nirvana.push.corex.publisher.Publisher;
import com.nirvana.push.corex.publisher.SimplePublisher;
import com.nirvana.push.corex.session.Client;
import com.nirvana.push.corex.session.MapSessionHall;
import com.nirvana.push.corex.session.Session;
import com.nirvana.push.corex.subscriber.Subscriber;
import com.nirvana.push.corex.subscriber.SubscriberStore;
import com.nirvana.push.corex.topic.MapTopicHall;
import com.nirvana.push.corex.topic.TopicHall;
import com.nirvana.push.protocol.BasePackage;

import java.util.Random;

/**
 * 协议业务处理
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class ProtocolProcess {

    private static ProtocolProcess INSTANCE;

    // 存储session
    private MapSessionHall sessionHall = MapSessionHall.getInstance();

    //topic 存储
    private TopicHall topicHall = MapTopicHall.getInstance();

    //订阅者 存储
    private SubscriberStore subscriberStore = SubscriberStore.getInstance();


    public static ProtocolProcess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProtocolProcess();
        }
        return INSTANCE;
    }


    /**
     * 客户端连接请求。
     */
    public void onConnect(Session session, BasePackage _package) {

        Client client = session.getClient();
        if (client == null) {
            client = new Client();
            client.setClientId(new Random().nextLong());
            session.bindClient(client);
            sessionHall.putSession((client).getClientId(), session);
        }

    }

    /**
     * 订阅请求。
     */
    public void onSubscribe(Session session, BasePackage _package) {
        Long sessionId = session.getSessionId();
        String topicName = "zhongc";
        Subscriber subscriber = subscriberStore.getSubscriberEnhance(sessionId);
        subscriber.subTopic(topicName);

    }

    /**
     * 接收到推送消息确认。
     */
    public void onPushMessageAck(Session session, BasePackage _package) {

    }

    /**
     * 接收到有且仅一次推送消息确认。
     */
    public void onExactlyOnceMessageAck(Session session, BasePackage _package) {

    }

    /**
     * 取消订阅请求。
     */
    public void onUnsubscribe(Session session, BasePackage _package) {

    }

    /**
     * 发布消息请求。
     */
    public void onPublish(Session session, BasePackage _package) {
        Long sessionId = session.getSessionId();
        String topic = "zhongc";
        Publisher publisher = new SimplePublisher(sessionId);
        //发布主题
        publisher.publish(topic);
        //想主题内发送消息
        publisher.pushMessage(topic, _package);
    }


    /**
     * 客户端心跳。
     */
    public void onPing(Session session, BasePackage _package) {

    }

    /**
     * 客户端断开连接请求。
     */
    public void onDisconnect(Session session, BasePackage _package) {

    }


    public void disConnect(Session session) {

        Client client = session.getClient();

        //从所有订阅的topic中将该session删除
        if (client != null) {
            Long sessionId = client.getClientId();


            if (subscriberStore.constanse(sessionId)) {
                Subscriber subscriber = subscriberStore.getSubscriber(sessionId);

                for (String topicName : subscriber.getSubTopics()) {

                    topicHall.getTopic(topicName).remvSubscriber(sessionId);
                }

            }
            sessionHall.remvSession(client.getClientId());
        }

        //关闭连接
        session.close();
    }


}
