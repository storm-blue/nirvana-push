package com.nirvana.xin.core.subscriber;

import com.nirvana.purist.core.subscriber.Subscriber;

/**
 * Created by Nirvana on 2017/11/18.
 */
public interface AcknowledgeSubscriber extends Subscriber {

    void onAcknowledgement(Object messageId);

}
