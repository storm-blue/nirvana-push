package com.nirvana.push.core;


import java.util.Collection;

/**
 * Topic是一个可以加入消息的结构。
 * 本身应只提供消息的存储以及消费。
 */
public interface Topic extends MessageHall {

    /**
     * 消费消息。获取消息的同时也会将消息从此topic中移除。
     *
     * @param maxNum 最大消费数量
     */
    Collection<Message> consume(int maxNum);

    /**
     * 单条消费。
     */
    Message consume();

    /**
     * 消费全部消息。
     */
    Collection<Message> consumeAll();

}