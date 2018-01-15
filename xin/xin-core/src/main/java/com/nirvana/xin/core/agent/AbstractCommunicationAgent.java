package com.nirvana.xin.core.agent;

import com.nirvana.xin.core.agent.exception.CommunicationException;
import com.nirvana.xin.core.agent.exception.ConnectionException;
import com.nirvana.xin.core.message.Package;
import com.nirvana.xin.core.message.MessageLevel;
import com.nirvana.xin.utils.StringUtils;

/**
 * Created by Nirvana on 2017/11/15.
 */
public abstract class AbstractCommunicationAgent extends AbstractAcknowledgeAgent {

    public AbstractCommunicationAgent(PackageDispatcher exchanger) {
        super(exchanger);
    }

    @Override
    protected final void doMessage(Package in, Package acknowledge) {
        switch (in.getType()) {
            case CONNECT:
                onConnect(in, acknowledge);
                break;
            case SUBSCRIBE:
                onSubscribe(in, acknowledge);
                break;
            case PUSH_MESSAGE_ACK:
                onPushMessageAck(in, acknowledge);
                break;
            case EXACTLY_ONCE_MESSAGE_ACK:
                onExactlyOnceMessageAck(in, acknowledge);
                break;
            case UNSUBSCRIBE:
                onUnsubscribe(in, acknowledge);
                break;
            case PUBLISH:
                onPublish(in, acknowledge);
                break;
            case PING:
                onPing(in, acknowledge);
                break;
            case DISCONNECT:
                onDisconnect(in, acknowledge);
                break;
            default:
                throw new CommunicationException("Unexpected message type.");
        }
    }

    /**
     * DST协议:[CONNECT]
     * receive:[-username\n-password]
     * output:[-OK]
     */
    private void onConnect(Package in, Package acknowledge) {
        String username = (String) in.getCardContent(0);
        String password = (String) in.getCardContent(1);
        if (username == null || password == null) {
            throw new CommunicationException("username and password must not be null.");
        }
        onConnect(username, password);
        acknowledge.addContent("OK");
    }

    /**
     * DST协议:[SUBSCRIBE]
     * receive:[-topicName]
     * output:[-OK]
     */
    private void onSubscribe(Package in, Package acknowledge) {
        String topicName = (String) in.getCardContent(0);
        if (StringUtils.isBlank(topicName)) {
            throw new CommunicationException("topic name must not be blank.");
        }
        onSubscribe(topicName);
        acknowledge.addContent("OK");
    }

    /**
     * DST协议:[PUSH_MESSAGE_ACK]
     */
    private void onPushMessageAck(Package in, Package acknowledge) {
        Object messageId = in.getCard(0);
        if (messageId != null) {
            onPushMessageAck(messageId);
        }
    }

    /**
     * DST协议:[EXACTLY_ONCE_MESSAGE_ACK]
     */
    private void onExactlyOnceMessageAck(Package in, Package acknowledge) {
        onPushMessageAck(in, acknowledge);
    }

    /**
     * DST协议:[UNSUBSCRIBE]
     * receive:[-topicName]
     * output:[-OK]
     */
    private void onUnsubscribe(Package in, Package acknowledge) {
        String topicName = (String) in.getCardContent(0);
        if (StringUtils.isBlank(topicName)) {
            throw new CommunicationException("topic name must not be blank.");
        }
        onUnsubscribe(topicName);
        acknowledge.addContent("OK");
    }

    /**
     * DST协议:[PUBLISH]
     * receive:[-topicName\n-message]
     * output:[-OK]
     */
    private void onPublish(Package in, Package acknowledge) {
        String topicName = (String) in.getCardContent(0);
        String message = (String) in.getCardContent(1);
        MessageLevel level;
        try {
            level = MessageLevel.valueOf((String) in.getCardContent(2));
        } catch (Exception e) {
            level = MessageLevel.NO_CONFIRM;
        }
        onPublish(topicName, message, level);
        acknowledge.addContent("OK");
    }

    /**
     * DST协议:[PING]
     */
    private void onPing(Package in, Package acknowledge) {
        onPing();
        acknowledge.addContent("OK");
    }

    /**
     * DST协议:[DISCONNECT]
     */
    private void onDisconnect(Package in, Package acknowledge) {
        onDisconnect();
    }

    /**
     * 客户端第一次连接服务器：登陆，鉴权，自动订阅主题等等。
     *
     * @throws ConnectionException 如果此过程中出现错误（例如鉴权错误），应当抛出此异常。此异常将会被捕获进行连接异常处理。
     */
    protected abstract void onConnect(String username, String password) throws ConnectionException;

    /**
     * 客户端订阅主题。
     */
    protected abstract void onSubscribe(String topicName);

    /**
     * 客户端确认收到推送消息。
     */
    protected abstract void onPushMessageAck(Object messageId);

    /**
     * 客户端确认收到有且仅一次推送消息。
     */
    protected abstract void onExactlyOnceMessageAck(Object messageId);

    /**
     * 客户端取消订阅。
     */
    protected abstract void onUnsubscribe(String topicName);

    /**
     * 客户端发布消息。
     */
    protected abstract void onPublish(String topicName, String message, MessageLevel level);

    /**
     * 客户端发送心跳。
     */
    protected abstract void onPing();

    /**
     * 客户端请求断开连接。
     */
    protected abstract void onDisconnect();
}
