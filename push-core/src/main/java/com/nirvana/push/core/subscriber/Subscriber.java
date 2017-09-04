package com.nirvana.push.core.subscriber;

/**
 * 消息订阅者。
 * Created by Nirvana on 2017/8/3.
 */
public interface Subscriber<T> {

    /**
     * 消息来了。处理一下。
     */
    void onMessage(T msg);

}
