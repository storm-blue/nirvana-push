package com.nirvana.push.core.agent;

import com.nirvana.push.core.AbstractDestroyable;
import com.nirvana.push.core.message.Card;
import com.nirvana.push.core.message.Package;
import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.message.PackageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract implement of Agent with Acknowledgement support.
 *
 * @see #maxSupportLevel
 * Created by Nirvana on 2017/8/7.
 */
public abstract class AbstractAcknowledgeAgent extends AbstractDestroyable implements Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAcknowledgeAgent.class);

    private static final Package DUMMY_ACKNOWLEDGE = new DummyPackage();

    protected MessageLevel maxSupportLevel = MessageLevel.AT_LEAST_ONCE;

    private ProtocolExchanger exchanger;

    public AbstractAcknowledgeAgent(ProtocolExchanger exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public final void onPackage(Package pkg) {
        MessageLevel level = pkg.getLevel();
        if (level.getCode() > maxSupportLevel.getCode()) {
            LOGGER.info("Message level: {} is over the max supported message level: {} , reduce it to {} level.", level, maxSupportLevel, maxSupportLevel);
            level = maxSupportLevel;
        }
        Package acknowledge = acknowledge(pkg.getType(), level, pkg.getId());

        try {
            doMessage(pkg, acknowledge);
        } catch (Exception e) {
            onException(e);
        }
        if (acknowledge != DUMMY_ACKNOWLEDGE) {
            //Do acknowledge
            sendPackage(acknowledge);
        }
    }

    protected void onException(Exception e) {
        disconnect();
    }

    private Package acknowledge(PackageType type, MessageLevel level, Object id) {
        switch (level) {
            case NO_CONFIRM:
                return DUMMY_ACKNOWLEDGE;
            case EXACTLY_ONCE:
                LOGGER.warn("Not supported message level: {}, Deal with this message with {} level instead.", MessageLevel.EXACTLY_ONCE, MessageLevel.AT_LEAST_ONCE);
                return acknowledge_AT_LEAST_ONCE(type, id);
            case AT_LEAST_ONCE:
                return acknowledge_AT_LEAST_ONCE(type, id);
            default:
                return DUMMY_ACKNOWLEDGE;
        }
    }

    private Package acknowledge_AT_LEAST_ONCE(PackageType type, Object id) {
        Package acknowledge;
        PackageType ack = type.getAck();
        if (ack == null) {
            acknowledge = DUMMY_ACKNOWLEDGE;
        } else {
            acknowledge = new Package(ack, MessageLevel.NO_CONFIRM, id);
        }
        return acknowledge;
    }

    protected abstract void doMessage(Package in, Package acknowledge);

    private static class DummyPackage extends Package {

        private DummyPackage() {super(null);}

        @Override
        public void addCard(Card card) {}

        @Override
        public void addContent(Object content) {}
    }

    @Override
    public void sendPackage(Package pkg) {
        exchanger.output(pkg);
    }

    @Override
    public void disconnect() {
        exchanger.close();
    }
}
