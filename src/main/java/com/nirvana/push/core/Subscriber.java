package com.nirvana.push.core;

/**
 * 消息订阅者。
 */
public interface Subscriber {

    /**
     * 消息来了。处理一下。
     */
    void onMessage(Message msg);

}
