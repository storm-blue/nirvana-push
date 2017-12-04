package com.nirvana.push.core.message;

import com.nirvana.push.core.broker.MessageBroker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nirvana on 2017/11/21.
 */
public class MessageMeta {

    private Object id;

    private MessageBroker broker;

    private Date createTime = new Date();

    private MessageLevel expectMessageLevel;

    private Map<String, Object> publisherInfo = new HashMap<>();

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public MessageBroker getBroker() {
        return broker;
    }

    public void setBroker(MessageBroker broker) {
        this.broker = broker;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MessageLevel getExpectMessageLevel() {
        return expectMessageLevel;
    }

    public void setExpectMessageLevel(MessageLevel expectMessageLevel) {
        this.expectMessageLevel = expectMessageLevel;
    }

    public Map<String, Object> getPublisherInfo() {
        return publisherInfo;
    }

    public void setPublisherInfo(Map<String, Object> publisherInfo) {
        this.publisherInfo = publisherInfo;
    }
}
