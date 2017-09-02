package com.nirvana.push.core.publisher;

import java.util.Collection;

/**
 * AbstractPublisher.java.
 * Created by Nirvana on 2017/9/1.
 */
public class AbstractPublisher<T> implements Publisher<T> {


    @Override
    public void publish(T msg) {

    }

    @Override
    public void publish(Collection<T> msg) {

    }
}
