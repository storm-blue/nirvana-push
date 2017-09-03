package com.nirvana.push.core.broker;

import java.util.Collection;

/**
 * 此消息代理不会自动的处理收到的消息，而是仅仅将消息加入到所持有的Topic中。
 * 当外部线程调用work()方法时，此代理才会工作一次。
 * Created by Nirvana on 2017/8/3.
 */
public class LazyMessageBroker extends MessageBroker {

    private Topic topic;

    public LazyMessageBroker(Object identifier) {
        super(identifier);
    }

    @Override
    public void putMessage(Object msg) {
        topic.putMessage(msg);
    }

    @Override
    public void putMessage(Collection<Object> messages) {
        topic.putMessage(messages);
    }

    public void work() {
        Collection<Object> messages = topic.consumeAll();
        handle(messages);
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
