package com.nirvana.push.corex;

import com.nirvana.push.corex.session.Client;
import com.nirvana.push.corex.session.MapSessionHall;
import com.nirvana.push.corex.session.Session;
import com.nirvana.push.corex.subscriber.SubscriberStore;
import com.nirvana.push.corex.topic.ITopic;
import com.nirvana.push.corex.topic.MapTopicHall;
import com.nirvana.push.corex.topic.Topic;
import com.nirvana.push.corex.topic.TopicHall;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.UTF8StringPayloadPart;

import java.util.Random;
import java.util.Set;

/**
 * 协议业务处理
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public class ProtocolProcess {

    private static ProtocolProcess INSTANCE;

    private MapSessionHall sessionHall;

    private TopicHall topicHall;

    private SubscriberStore subscriberStore;

    public ProtocolProcess() {
        sessionHall = new MapSessionHall();
        topicHall = new MapTopicHall();
        subscriberStore =new SubscriberStore();
    }


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

    }

    /**
     * 订阅请求。
     */
    public void onSubscribe(Session session, BasePackage _package) {


        Client client = session.getClient();
        if (client == null) {
            client = new Client();
            client.setClientId(new Random().nextLong());
            session.bindClient(client);
            sessionHall.putSession((client).getClientId(), session);
        }

        ITopic topic = topicHall.getTopic("zhongc");

        if (topic != null) {
            topic.addSubscriber(session);
            subscriberStore.addTopicForSub(client.getClientId(),topic);
        }
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
        Client client = new Client();
        client.setClientId(new Random().nextLong());
        session.bindClient(client);
        sessionHall.putSession(client.getClientId(), session);

        if (topicHall.contains("zhongc")) {


            String message = new UTF8StringPayloadPart(_package.getPayload().getByteBuf()).getMessage();

            System.out.println("发布消息 ：" + message);

            topicHall.getTopic("zhongc").onMessage(message);


        } else {

            ITopic topic = new Topic(session, "zhongc");
            topicHall.addTopic(topic);
        }


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
            Set<ITopic> topics = subscriberStore.getTopicsBySub(client.getClientId());

            if (topics != null) {

                for (ITopic topic : topics) {

                    if (sessionHall.isOnline(client.getClientId())) {
                        topic.remvSubscriber(sessionHall.getSession(client.getClientId()));
                    }

                }
            }

        }

        sessionHall.remvSession(client.getClientId());

        //关闭连接
        session.close();
    }


}
