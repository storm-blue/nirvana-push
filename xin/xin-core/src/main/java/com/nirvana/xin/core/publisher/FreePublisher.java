package com.nirvana.xin.core.publisher;

import com.nirvana.purist.core.message.Message;

import java.util.Collection;

/**
 * 自由发布者。
 * Created by Nirvana on 2017/9/3.
 */
public interface FreePublisher extends Publisher {

    void publish(Message message);

    void publish(Collection<Message> messages);

}
