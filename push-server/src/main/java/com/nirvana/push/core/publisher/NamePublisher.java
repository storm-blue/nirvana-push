package com.nirvana.push.core.publisher;

import java.util.Collection;

/**
 * 根据主题名称发布。
 * Created by Nirvana on 2017/9/3.
 */
public interface NamePublisher<T> extends Publisher {

    void publish(String name, T msg);

    void publish(String name, Collection<T> msg);

}
