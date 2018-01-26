package com.nirvana.purist.core.message;


import com.nirvana.purist.core.broker.MessageBroker;

import java.util.Date;

/**
 * Created by Nirvana on 2017/11/16.
 */
public class Message {

    private Object id;

    private MessageBroker broker;

    private Date createTime;

    private MessageLevel expectMessageLevel;

    private CardBox publisherInfo;

    private CardBox content;

    private volatile MessageStatus status;

    public Message(CardBox publisherInfo, CardBox content) {
        this(new Date(), MessageLevel.NO_CONFIRM, publisherInfo, content);
    }

    public Message(Date createTime, CardBox publisherInfo, CardBox content) {
        this(createTime, MessageLevel.NO_CONFIRM, publisherInfo, content);
    }

    public Message(Date createTime, MessageLevel expectMessageLevel, CardBox publisherInfo, CardBox content) {
        this.createTime = createTime;
        this.expectMessageLevel = expectMessageLevel;
        this.publisherInfo = publisherInfo;
        this.content = content;
        status = MessageStatus.CREATED;
    }

    public void grant(MessageBroker broker) {
        if (status != MessageStatus.CREATED) {
            throw new IllegalStateException("find wrong state: " + status + "when try to grant a message object.");
        }
        this.broker = broker;
        status = MessageStatus.GRANTED;
    }

    public void identify(Object id) {
        if (status != MessageStatus.GRANTED) {
            throw new IllegalStateException("find wrong state: " + status + "when try to grant a message object.");
        }
        this.id = id;
        status = MessageStatus.IDENTIFIED;
    }

    public Object getId() {
        return id;
    }

    public MessageBroker getBroker() {
        return broker;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public MessageLevel getExpectMessageLevel() {
        return expectMessageLevel;
    }

    public CardBox getPublisherInfo() {
        return publisherInfo;
    }

    public CardBox getContent() {
        return content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public enum MessageStatus {
        CREATED, GRANTED, IDENTIFIED
    }
}