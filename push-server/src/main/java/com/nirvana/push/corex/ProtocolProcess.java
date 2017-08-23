package com.nirvana.push.corex;

import com.nirvana.push.corex.session.MapSessionHall;
import com.nirvana.push.corex.session.Session;
import com.nirvana.push.corex.topic.MapTopicHall;
import com.nirvana.push.corex.topic.TopicHall;
import com.nirvana.push.protocol.BasePackage;

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

    public ProtocolProcess() {
        sessionHall = new MapSessionHall();
        topicHall = new MapTopicHall();
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


    public void disConnect(Session session){

    }


}
