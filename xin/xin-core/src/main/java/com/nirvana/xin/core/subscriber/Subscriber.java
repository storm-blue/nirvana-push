package com.nirvana.xin.core.subscriber;

import com.nirvana.xin.core.message.Message;

/**
 * 消息订阅者。
 * Created by Nirvana on 2017/8/3.
 */
public interface Subscriber {

    /**
     * 消息来了。处理一下。
     */
    void onMessage(Message message);

}
