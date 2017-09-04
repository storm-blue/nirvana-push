package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.agent.Agent;
import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * String消息订阅者。
 * Created by Nirvana on 2017/8/16.
 */
public class StringSubscriber implements Subscriber<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringSubscriber.class);

    private Agent agent;

    public StringSubscriber(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void onMessage(String msg) {
        LOGGER.debug("订阅器开始处理获得的消息：{}", msg);
        agent.sendPackage(new BasePackage(PackageType.PUSH_MESSAGE, PackageLevel.NO_CONFIRM, false, false, null, new UTF8StringPayloadPart(msg)));
    }
}
