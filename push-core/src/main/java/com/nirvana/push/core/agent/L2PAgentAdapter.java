package com.nirvana.push.core.agent;

import com.nirvana.push.core.DestroyFailedException;
import com.nirvana.push.core.agent.exception.ConnectException;
import com.nirvana.push.core.publisher.DefaultNamePublisher;
import com.nirvana.push.core.publisher.NamePublisher;
import com.nirvana.push.core.subscriber.AgentSubscriber;
import com.nirvana.push.protocol.*;
import com.nirvana.push.protocol.l2.L2Package;
import com.nirvana.push.protocol.l2.L2ProtocolException;
import io.netty.buffer.ByteBuf;

import java.util.List;


/**
 * DST协议的实现。TODO:精准的消息级别，有序消息等等。
 * 暂未使用Base协议中的identifier字段。此字段可用于消息确认标识。后期实现此服务在此类中实现。
 * Created by Nirvana on 2017/9/5.
 */
public abstract class L2PAgentAdapter extends AbstractAgent {

    private PackageLevel packageLevel = PackageLevel.NO_CONFIRM;

    protected AgentSubscriber subscriber = new AgentSubscriber(this);

    protected NamePublisher<String> publisher = new DefaultNamePublisher<>();

    //TODO 通过strategy控制消息服务级别，消息identifier等行为。
    private L2PCodecStrategy l2PCodecStrategy = DSTCodecStrategy.getStrategy();

    /**
     * DST协议:[CONNECT]
     * receive:[-username\n-password]
     * output:[-OK]
     */
    @Override
    protected final void onConnect(Long identifier, ByteBuf data) {
        try {
            L2Package pkg = l2PCodecStrategy.decode(data);
            if (pkg.size() != 2) {
                throw new L2ProtocolException();
            }
            String username = (String) pkg.get(0);
            String password = (String) pkg.get(1);
            if (username == null || password == null) {
                throw new L2ProtocolException();
            }
            onConnect(username, password);
            L2Package ack = l2PCodecStrategy.encodeValues("OK");
            sendPackage(new BasePackage(PackageType.CONNECT_ACK, PackageLevel.NO_CONFIRM, false, identifier, new PayloadPart(ack.getByteBuf())));
        } catch (L2ProtocolException e) {
            onProtocolException(e);
        } catch (ConnectException e) {
            onConnectException(e);
        }
    }

    /**
     * DST协议:[SUBSCRIBE]
     * receive:[-topicName]
     * output:[-OK]
     */
    @Override
    protected final void onSubscribe(Long identifier, ByteBuf data) {
        try {
            L2Package pkg = l2PCodecStrategy.decode(data);
            if (pkg.size() != 1) {
                throw new L2ProtocolException();
            }
            String topicName = (String) pkg.get(0);
            if (topicName == null) {
                throw new L2ProtocolException();
            }
            onSubscribe(topicName);
        } catch (L2ProtocolException e) {
            onProtocolException(e);
        }
    }

    /**
     * DST协议:[PUSH_MESSAGE_ACK]
     */
    @Override
    protected final void onPushMessageAck(Long identifier, ByteBuf data) {
        onPushMessageAck();
    }

    /**
     * DST协议:[EXACTLY_ONCE_MESSAGE_ACK]
     */
    @Override
    protected final void onExactlyOnceMessageAck(Long identifier, ByteBuf data) {
        onExactlyOnceMessageAck();
    }

    /**
     * DST协议:[UNSUBSCRIBE]
     * receive:[-topicName]
     * output:[-OK]
     */
    @Override
    protected final void onUnsubscribe(Long identifier, ByteBuf data) {
        try {
            L2Package pkg = l2PCodecStrategy.decode(data);
            if (pkg.size() != 1) {
                throw new L2ProtocolException();
            }
            String topicName = (String) pkg.get(0);
            if (topicName == null) {
                throw new L2ProtocolException();
            }
            onUnsubscribe(topicName);
        } catch (L2ProtocolException e) {
            onProtocolException(e);
        }
    }

    /**
     * DST协议:[PUBLISH]
     * receive:[-topicName\n-message]
     * output:[-OK]
     */
    @Override
    protected final void onPublish(PackageLevel level, boolean retain, Long identifier, ByteBuf data) {
        try {
            L2Package pkg = l2PCodecStrategy.decode(data);
            if (pkg.size() != 2) {
                throw new L2ProtocolException();
            }
            String topicName = (String) pkg.get(0);
            if (topicName == null) {
                throw new L2ProtocolException();
            }
            String message = (String) pkg.get(1);
            if (message == null) {
                throw new L2ProtocolException();
            }
            onPublish(topicName, message);
        } catch (L2ProtocolException e) {
            onProtocolException(e);
        }
    }

    /**
     * DST协议:[PING]
     */
    @Override
    protected final void onPing(Long identifier, ByteBuf data) {
        onPing();
    }

    /**
     * DST协议:[DISCONNECT]
     */
    @Override
    protected final void onDisconnect(Long identifier, ByteBuf data) {
        onDisconnect();
    }

    /**
     * 协议解析错误时的处理。子类可覆盖此方法。
     */
    protected void onProtocolException(L2ProtocolException e) {
        disconnect();
    }

    /**
     * 连接错误处理，子类可覆盖此方法。
     */
    protected void onConnectException(ConnectException e) {
        disconnect();
    }

    /**
     * 客户端第一次连接服务器：登陆，鉴权，自动订阅主题等等。
     *
     * @throws ConnectException 如果此过程中出现错误（例如鉴权错误），应当抛出此异常。此异常将会被捕获进行连接异常处理。
     * @see #onConnectException(ConnectException) 连接异常处理方法
     */
    protected abstract void onConnect(String username, String password) throws ConnectException;

    /**
     * 客户端订阅主题。
     */
    protected void onSubscribe(String topicName) {
        subscriber.subscribe(topicName);
    }

    /**
     * 客户端确认收到推送消息。
     */
    protected void onPushMessageAck() {
    }

    /**
     * 客户端确认收到有且仅一次推送消息。
     */
    protected void onExactlyOnceMessageAck() {
    }

    /**
     * 客户端取消订阅。
     */
    protected void onUnsubscribe(String topicName) {
        subscriber.unsubscribe(topicName);
    }

    /**
     * 客户端发布消息。
     */
    protected void onPublish(String topicName, String message) {
        publisher.publish(topicName, message);
    }

    /**
     * 客户端发送心跳。
     */
    protected void onPing() {
    }

    /**
     * 客户端请求断开连接。
     */
    protected void onDisconnect() {
    }

    @Override
    protected void doDestroy() throws DestroyFailedException {
        subscriber.destroy();
    }

    /**
     * 向客户端推送推送消息。
     */
    public void pushMessage(String msg) {
        sendPackage(new BasePackage(PackageType.PUSH_MESSAGE, packageLevel, false, null, l2PCodecStrategy.encodeValues(msg).getByteBuf()));
    }

    /**
     * 批量向客户端推送推送消息。
     */
    public void pushMessage(List<String> msg) {
    }
}
