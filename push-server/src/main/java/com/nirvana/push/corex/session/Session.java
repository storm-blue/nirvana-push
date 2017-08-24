package com.nirvana.push.corex.session;

import com.nirvana.push.corex.publisher.Publisher;
import com.nirvana.push.corex.subscriber.Subscriber;

/**
 *  对channel进行的包装
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface Session extends Subscriber,Publisher {
    /**
     * 会话绑定对象
     * @return
     */
    Client getClient();

    /**
     * 绑定对象
     * @return
     */
    void bindClient(Client attachment);

    /**
     * 移除绑定对象
     * @return
     */
    void removeClient();

    /**
     * 向会话中写入消息
     * @param message
     */
    void write(Object message);

    /**
     * 判断会话是否在连接中
     * @return
     */
    boolean isConnected();

    /**
     * 关闭
     * @return
     */
    void close();




}
