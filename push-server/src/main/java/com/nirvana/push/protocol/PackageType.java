package com.nirvana.push.protocol;

import com.nirvana.push.utils.CodeEnumerator;

/**
 * com.nirvana.push.protocol.PackageType.java.
 * <p>
 * Created by Nirvana on 2017/8/9.
 */
public enum PackageType {

    /*连接*/
    CONNECT(0x01),

    /*连接确认*/
    CONNECT_ACK(0x02),

    /*订阅*/
    SUBSCRIBE(0x03),

    /*订阅确认*/
    SUBSCRIBE_ACK(0x04),

    /*推送消息*/
    PUSH_MESSAGE(0x05),

    /*推送消息确认*/
    PUSH_MESSAGE_ACK(0x06),

    /*有且仅一次消息确认*/
    EXACTLY_ONCE_MESSAGE_ACK(0x07),

    /*取消订阅*/
    UNSUBSCRIBE(0x08),

    /*取消订阅确认*/
    UNSUBSCRIBE_ACK(0x09),

    /*发布*/
    PUBLISH(0x0A),

    /*发布确认*/
    PUBLISH_ACK(0x0B),

    /*心跳*/
    PING(0x0C),

    /*心跳确认*/
    PING_ACK(0x0D),

    /*断开连接*/
    DISCONNECT(0x0E);

    private int code;

    PackageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final CodeEnumerator<PackageType> enumerator = new CodeEnumerator<>(PackageType.class);

    public static PackageType get(int code) {
        return enumerator.get(code);
    }

}
