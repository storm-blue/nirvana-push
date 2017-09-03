package com.nirvana.push.core.broker;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的Broker管理器。
 * Created by Nirvana on 2017/9/3.
 */
public class MessageBrokerSource {

    private static final MessageBrokerSource instance = new MessageBrokerSource();

    private Map<String, MessageBroker> brokers = new ConcurrentHashMap<>();

    private MessageBrokerSource() {
    }

    public static MessageBrokerSource getSource() {
        return instance;
    }

    /**
     * 以此名称在Source中创建新的MessageBroker.
     *
     * @param name broker名称
     * @return 返回创建的Broker
     */
    public MessageBroker create(String name) {
        MessageBroker broker = brokers.get(name);
        if (broker == null) {
            synchronized (this) {
                if ((broker = brokers.get(name)) != null) {
                    return broker;
                }
                broker = new AutoMessageBroker(name);
                brokers.put(name, broker);
            }
        }
        return broker;
    }

    /**
     * 根据名称获取MessageBroker.
     *
     * @return 如果不存在，返回null
     */
    public MessageBroker get(String name) {
        return brokers.get(name);
    }

    Iterator<MessageBroker> interator() {

        return new Iterator<MessageBroker>() {

            private Iterator<String> iterator = brokers.keySet().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public MessageBroker next() {
                return brokers.get(iterator.next());
            }
        };

    }
}
