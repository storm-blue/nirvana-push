package com.nirvana.xin.core.publisher;

import com.nirvana.purist.core.message.Message;

import java.util.Collection;

/**
 * 根据主题名称发布。
 * Created by Nirvana on 2017/9/3.
 */
public interface NamePublisher extends Publisher {

    void publish(String name, Message msg);

    void publish(String name, Collection<Message> msg);

}
