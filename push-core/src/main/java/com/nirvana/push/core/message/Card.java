package com.nirvana.push.core.message;

/**
 * Created by Nirvana on 2017/11/15.
 */
public interface Card<T> {

    String getName();

    T getContent();

}
