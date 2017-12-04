package com.nirvana.push.core.subscriber;

/**
 * Created by Nirvana on 2017/11/18.
 */
public interface AcknowledgeSubscriber extends Subscriber {

    void onAcknowledgement(Object messageId);

}
