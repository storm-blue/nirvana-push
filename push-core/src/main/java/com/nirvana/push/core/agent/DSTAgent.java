package com.nirvana.push.core.agent;

import com.nirvana.push.core.agent.exception.ConnectException;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import com.nirvana.push.protocol.exception.ProtocolException;
import com.nirvana.push.protocol.p2.DSTElement;
import com.nirvana.push.protocol.p2.DSTPackage;
import com.nirvana.push.protocol.p2.DSTPayloadPart;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * DST协议的实现。
 * 暂未使用Base协议中的identifier字段。此字段可用于消息确认标识。后期实现此服务在此类中实现。
 * Created by Nirvana on 2017/9/5.
 */
public abstract class DSTAgent extends AbstractAgent {

    private PackageLevel packageLevel = PackageLevel.NO_CONFIRM;

    /**
     * receive:[-username\n-password]
     * output:[-OK]
     */
    @Override
    protected final void onConnect(Long identifier, ByteBuf data) {
        try {
            DSTPackage pkg = getDSTPackage(data);
            if (pkg.size() != 2) {
                throw new ProtocolException();
            }
            String username = pkg.get(0);
            String password = pkg.get(1);
            if (username == null || password == null) {
                throw new ProtocolException();
            }
            onConnect(username, password);
            DSTPackage ack = new DSTPackage(new DSTElement("OK"));
            sendPackage(new BasePackage(PackageType.CONNECT_ACK, PackageLevel.NO_CONFIRM, false, identifier, new UTF8StringPayloadPart(ack.getContent())));
        } catch (ProtocolException e) {
            onProtocolException(e);
        } catch (ConnectException e) {
            onConnectException(e);
        }
    }

    /**
     * receive:[-topicName]
     * output:[-OK]
     */
    @Override
    protected final void onSubscribe(Long identifier, ByteBuf data) {
        try {
            DSTPackage pkg = getDSTPackage(data);
            if (pkg.size() != 1) {
                throw new ProtocolException();
            }
            String topicName = pkg.get(0);
            if (topicName == null) {
                throw new ProtocolException();
            }
            onSubscribe(topicName);
        } catch (ProtocolException e) {
            onProtocolException(e);
        }
    }

    @Override
    protected final void onPushMessageAck(Long identifier, ByteBuf data) {
        onPushMessageAck();
    }

    @Override
    protected final void onExactlyOnceMessageAck(Long identifier, ByteBuf data) {
        onExactlyOnceMessageAck();
    }

    /**
     * receive:[-topicName]
     * output:[-OK]
     */
    @Override
    protected final void onUnsubscribe(Long identifier, ByteBuf data) {
        try {
            DSTPackage pkg = getDSTPackage(data);
            if (pkg.size() != 1) {
                throw new ProtocolException();
            }
            String topicName = pkg.get(0);
            if (topicName == null) {
                throw new ProtocolException();
            }
            onUnsubscribe(topicName);
        } catch (ProtocolException e) {
            onProtocolException(e);
        }
    }

    /**
     * receive:[-topicName-message]
     * output:[-OK]
     */
    @Override
    protected final void onPublish(PackageLevel level, boolean retain, Long identifier, ByteBuf data) {
        try {
            DSTPackage pkg = getDSTPackage(data);
            if (pkg.size() != 2) {
                throw new ProtocolException();
            }
            String topicName = pkg.get(0);
            if (topicName == null) {
                throw new ProtocolException();
            }
            String message = pkg.get(1);
            if (message == null) {
                throw new ProtocolException();
            }
            onPublish(topicName, message);
        } catch (ProtocolException e) {
            onProtocolException(e);
        }
    }

    @Override
    protected final void onPing(Long identifier, ByteBuf data) {
        onPing();
    }

    @Override
    protected final void onDisconnect(Long identifier, ByteBuf data) {
        onDisconnect();
    }

    private DSTPackage getDSTPackage(ByteBuf buf) {
        String content = buf.toString(Charset.forName("UTF-8"));
        return new DSTPackage(content);
    }

    /**
     * 协议解析错误时的处理。子类可覆盖此方法。
     */
    protected void onProtocolException(ProtocolException e) {
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
     * @see #onConnectException(ConnectException)
     */
    protected abstract void onConnect(String username, String password) throws ConnectException;

    /**
     * 客户端订阅主题。
     */
    protected abstract void onSubscribe(String topicName);

    /**
     * 客户端确认收到推送消息。
     */
    protected abstract void onPushMessageAck();

    /**
     * 客户端确认收到有且仅一次推送消息。
     */
    protected abstract void onExactlyOnceMessageAck();

    /**
     * 客户端取消订阅。
     */
    protected abstract void onUnsubscribe(String topicName);

    /**
     * 客户端发布消息。
     */
    protected abstract void onPublish(String topicName, String message);

    /**
     * 客户端发送心跳。
     */
    protected abstract void onPing();

    /**
     * 客户端请求断开连接。
     */
    protected abstract void onDisconnect();

    public void sendPackage(PackageType type, boolean retain, Long identifier, DSTPackage pkg) {
        sendPackage(new BasePackage(type, packageLevel, retain, identifier, new DSTPayloadPart(pkg)));
    }
}
