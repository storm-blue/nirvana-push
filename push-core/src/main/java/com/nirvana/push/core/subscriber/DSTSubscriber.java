package com.nirvana.push.core.subscriber;

import com.nirvana.push.core.DestroyFailedException;
import com.nirvana.push.core.Destroyable;
import com.nirvana.push.core.agent.DSTAgent;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.p2.DSTPackage;

/**
 * DSTSubscriber.java.
 * Created by Nirvana on 2017/9/7.
 */
public class DSTSubscriber implements Subscriber<String>, Destroyable {

    private DSTAgent agent;

    public DSTSubscriber(DSTAgent agent) {
        this.agent = agent;
    }

    @Override
    public void onMessage(String msg) {
        DSTPackage dstPackage = new DSTPackage(new String[]{msg});
        agent.sendPackage(PackageType.PUSH_MESSAGE, false, null, dstPackage);
    }


    @Override
    public void destroy() throws DestroyFailedException {

    }

    @Override
    public DestroyStatus destroyStatus() {
        return null;
    }
}
