package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.agent.L2PAgentAdapter;
import com.nirvana.push.core.broker.MessageBroker;
import com.nirvana.push.core.broker.MessageBrokerSource;

/**
 * DST协议订阅者实现。
 * Created by Nirvana on 2017/9/7.
 */
public class AgentSubscriber extends InitiativeSubscriber<String> {

    private MessageBrokerSource brokerSource = MessageBrokerSource.getSource();

    //DST协议代理类实现。
    private L2PAgentAdapter agent;

    public AgentSubscriber(L2PAgentAdapter agent) {
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
     * 通过Agent向外部推送消息。
     */
    @Override
    public void onMessage(String msg) {
        agent.pushMessage(msg);
    }

}
