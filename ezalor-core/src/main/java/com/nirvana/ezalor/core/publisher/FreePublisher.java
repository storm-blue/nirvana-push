package com.nirvana.ezalor.core.publisher;

import java.util.Collection;

/**
 * 自由发布者。
 * Created by Nirvana on 2017/9/3.
 */
public interface FreePublisher extends Publisher {

    void publish(Object message);

    void publish(Collection<Object> messages);

}
