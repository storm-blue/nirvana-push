package com.nirvana.ezalor.core.subscriber;

import com.nirvana.ezalor.core.agent.Agent;
import com.nirvana.ezalor.core.message.Message;
import com.nirvana.ezalor.core.message.MessageLevel;
import com.nirvana.ezalor.core.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nirvana on 2017/11/17.
 */
public abstract class ProgressedSubscriber extends AgentSubscriber {

    private static final String BROKER_PROGRESS_SESSION_KEY = "_markableSubscriber_broker_progress";
    private static final String PRE_PROGRESS_SESSION_KEY = "_markableSubscriber_pre_progress";

    protected MessageLevel messageLevel = MessageLevel.AT_LEAST_ONCE;

    public ProgressedSubscriber(Agent agent, Session session) {
        super(agent, session);
        session.setAttribute(BROKER_PROGRESS_SESSION_KEY, new ConcurrentHashMap<>());
        session.setAttribute(PRE_PROGRESS_SESSION_KEY, new ConcurrentHashMap<>());
    }

    @Override
    public void onMessage(Message message) {
        long sessionSerialNumber = obtainSerialNumber();
        String brokerId = message.getBroker().getId();
        sendMessage(message, messageLevel, sessionSerialNumber);
        //messageLevel = MessageLevel.NO_CONFIRM, update progress.
        if (messageLevel == MessageLevel.NO_CONFIRM) {
            setProgress(brokerId, (Long) message.getId());
        }
        //messageLevel = MessageLevel.AT_LEAST_ONCE, pre progress.
        else if (messageLevel == MessageLevel.AT_LEAST_ONCE) {
            preProgress(sessionSerialNumber, brokerId, (Long) message.getId());
        }
    }

    @Override
    public void onAcknowledgement(Object messageId) {
        if (messageLevel == MessageLevel.AT_LEAST_ONCE) {
            commitProgress((Long) messageId);
        }
    }

    @SuppressWarnings("unchecked")
    private void setProgress(String key, long mark) {
        Map<String, Long> brokerMarks = (Map<String, Long>) session.getAttribute(BROKER_PROGRESS_SESSION_KEY);
        brokerMarks.put(key, mark);
    }

    @SuppressWarnings("unchecked")
    public long getProgress(String key) {
        Map<String, Long> brokerMarks = (Map<String, Long>) session.getAttribute(BROKER_PROGRESS_SESSION_KEY);
        return brokerMarks.get(key) == null ? 0 : brokerMarks.get(key);
    }

    @SuppressWarnings("unchecked")
    private void preProgress(long serialNumber, String key, long mark) {
        Map<Long, PreProgress> preProgresses = (Map<Long, PreProgress>) session.getAttribute(PRE_PROGRESS_SESSION_KEY);
        PreProgress preProgress = new PreProgress(key, mark);
        preProgresses.put(serialNumber, preProgress);
    }

    @SuppressWarnings("unchecked")
    private void commitProgress(long serialNumber) {
        Map<Long, PreProgress> preProgresses = (Map<Long, PreProgress>) session.getAttribute(PRE_PROGRESS_SESSION_KEY);
        PreProgress preProgress = preProgresses.get(serialNumber);
        if (preProgress == null) {
            return;
        }
        if (preProgress.getProgress() > getProgress(preProgress.getBrokerName())) {
            setProgress(preProgress.getBrokerName(), preProgress.getProgress());
        }
    }

    private static class PreProgress {

        private String brokerName;

        private long progress;

        PreProgress(String brokerName, long progress) {
            this.brokerName = brokerName;
            this.progress = progress;
        }

        String getBrokerName() {
            return brokerName;
        }

        long getProgress() {
            return progress;
        }

    }
}
