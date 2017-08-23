package com.nirvana.push.corex.subscriber;



/**
 *  订阅者
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface Subscriber {

    //收到消息
    void onMessage( Object message);

}
