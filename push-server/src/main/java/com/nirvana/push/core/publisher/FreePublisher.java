package com.nirvana.push.core.publisher;

import java.util.Collection;

/**
 * 自由发布者。
 * Created by Nirvana on 2017/9/3.
 */
public interface FreePublisher<T> extends Publisher {

    void publish(T msg);

    void publish(Collection<T> msg);

}
