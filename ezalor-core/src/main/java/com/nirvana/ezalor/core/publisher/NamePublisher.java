package com.nirvana.ezalor.core.publisher;

import java.util.Collection;

/**
 * 根据主题名称发布。
 * Created by Nirvana on 2017/9/3.
 */
public interface NamePublisher extends Publisher {

    void publish(String name, Object msg);

    void publish(String name, Collection<Object> msg);

}
