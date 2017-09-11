package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.agent.DSTAgent;
import com.nirvana.push.core.broker.MessageBroker;
import com.nirvana.push.core.broker.MessageBrokerSource;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.p2.DSTPackage;

/**
 * DST协议订阅者实现。
 * Created by Nirvana on 2017/9/7.
 */
public class DSTSubscriber extends InitiativeSubscriber<String> {

    private MessageBrokerSource brokerSource = MessageBrokerSource.getSource();

    //DST协议代理类实现。
    private DSTAgent agent;

    public DSTSubscriber(DSTAgent agent) {
        this.agent = agent;
    }

    /**
     * 根据名称订阅。
     */
    public void subscribe(String brokerName) {
        MessageBroker broker = brokerSource.createIfAbsent(brokerName);
        subscribe(broker);
    }

    /**
     * 根据名称取消订阅。
     */
    public void unsubscribe(String brokerName) {
        MessageBroker broker = brokerSource.createIfAbsent(brokerName);
        unsubscribe(broker);
    }

    /**
     * 发送DST协议消息。
     * TODO 更精确的消息级别控制，identifier字段控制。
     */
    @Override
    public void onMessage(String msg) {
        DSTPackage dstPackage = new DSTPackage(new String[]{msg});
        //agent.sendPackage(PackageType.PUSH_MESSAGE, false, null, dstPackage);
    }

}
