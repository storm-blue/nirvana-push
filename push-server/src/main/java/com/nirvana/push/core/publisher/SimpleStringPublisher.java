package com.nirvana.push.core.publisher;

import com.nirvana.push.core.broker.MessageHall;

/**
 * 简单写入到默认MessageBroker的发布者。
 * Created by Nirvana on 2017/8/17.
 */
public class SimpleStringPublisher extends AbstractBindHallPublisher<String> {

    public SimpleStringPublisher() {
    }

    public SimpleStringPublisher(MessageHall hall) {
        super(hall);
    }
}
