package com.nirvana.push.core.broker;

import com.nirvana.push.core.message.Message;
import com.nirvana.push.core.message.MessageMeta;
import com.nirvana.push.core.message.SimpleCard;
import com.nirvana.push.core.subscriber.ProgressedSubscriber;
import com.nirvana.push.core.subscriber.Subscriber;
import com.nirvana.push.utils.tuple.Pair;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Nirvana on 2017/11/19.
 */
public class ProgressShelduleMessageBroker extends MessageBroker {

    private MarkableMessageSource messageSource = new OuroborosMessageSource();

    //whether use batch push mode.
    private boolean batch = false;

    //if batchPush == true, set the size every batch.
    private int maxBatchSize = 20;

    public ProgressShelduleMessageBroker(String id) {
        super(id);
    }

    @Override
    protected void beforeAdd(Subscriber subscriber) {
        if (!(subscriber instanceof ProgressedSubscriber)) {
            throw new IllegalArgumentException("Expect ProgressedSubscriber.class by found: " + subscriber.getClass().getSimpleName());
        }
    }

    @Override
    public void work() {
        for (Subscriber subscriber : subscribers) {
            long progress = ((ProgressedSubscriber) subscriber).getProgress(getId());

            //batch consumer.
            if (batch) {
                Pair<Long, Collection<Object>> pair = messageSource.consumer(progress, maxBatchSize);
                MessageMeta meta = new MessageMeta();
                meta.setBroker(this);
                meta.setCreateTime(new Date());
                meta.setId(pair.getE1());
                Message message = new Message(meta);
                for (Object object : pair.getE2()) {
                    message.addCard(new SimpleCard(object));
                }
                subscriber.onMessage(message);
            }

            //single consumer.
            else {
                Pair<Long, Object> pair = messageSource.consumer(progress);
                MessageMeta meta = new MessageMeta();
                meta.setBroker(this);
                meta.setCreateTime(new Date());
                meta.setId(pair.getE1());
                Message message = new Message(meta);
                message.addCard(new SimpleCard(pair.getE2()));
                subscriber.onMessage(message);
            }
        }
    }


    @Override
    public void putMessage(Object message) {
        messageSource.putMessage(message);
    }

    @Override
    public void putMessage(Collection<Object> messages) {
        messageSource.putMessage(messages);
    }

    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public int getMaxBatchSize() {
        return maxBatchSize;
    }

    public void setMaxBatchSize(int maxBatchSize) {
        if (maxBatchSize <= 0) {
            throw new IllegalArgumentException("batch size must greater than zero.");
        }
        this.maxBatchSize = maxBatchSize;
    }
}
