package com.nirvana.xin.core.message;

import com.nirvana.xin.utils.CodeEnumerator;

/**
 * PackageType.java.
 * <p>
 * Created by Nirvana on 2017/8/9.
 */
public enum PackageType {

    /*连接确认*/
    CONNECT_ACK(0x02),

    /*连接*/
    CONNECT(0x01, PackageType.CONNECT_ACK),

    /*订阅确认*/
    SUBSCRIBE_ACK(0x04),

    /*订阅*/
    SUBSCRIBE(0x03, PackageType.SUBSCRIBE_ACK),

    /*推送消息确认*/
    PUSH_MESSAGE_ACK(0x06),

    /*推送消息*/
    PUSH_MESSAGE(0x05, PackageType.PUSH_MESSAGE_ACK),

    /*有且仅一次消息确认*/
    EXACTLY_ONCE_MESSAGE_ACK(0x07),

    /*取消订阅确认*/
    UNSUBSCRIBE_ACK(0x09),

    /*取消订阅*/
    UNSUBSCRIBE(0x08, PackageType.UNSUBSCRIBE_ACK),

    /*发布确认*/
    PUBLISH_ACK(0x0B),

    /*发布*/
    PUBLISH(0x0A, PackageType.PUBLISH_ACK),

    /*心跳确认*/
    PING_ACK(0x0D),

    /*心跳*/
    PING(0x0C, PackageType.PING_ACK),

    /*断开连接*/
    DISCONNECT(0x0E);

    private int code;

    private PackageType ack;

    PackageType(int code) {
        this.code = code;
    }

    PackageType(int code, PackageType ack) {
        this.code = code;
        this.ack = ack;
    }

    public int getCode() {
        return code;
    }

    public PackageType getAck() {
        return ack;
    }

    public static CodeEnumerator<PackageType> getEnumerator() {
        return enumerator;
    }

    private static final CodeEnumerator<PackageType> enumerator = new CodeEnumerator<>(PackageType.class);

    public static PackageType get(int code) {
        return enumerator.get(code);
    }

}
