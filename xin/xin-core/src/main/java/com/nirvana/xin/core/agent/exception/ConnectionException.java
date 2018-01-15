package com.nirvana.xin.core.agent.exception;

/**
 * Created by Nirvana on 2017/9/6.
 */
public class ConnectionException extends CommunicationException {

    private static final String MESSAGE = "Connection异常。";

    public ConnectionException() {
        super(MESSAGE);
    }

    public ConnectionException(String message) {
        super(message);
    }

}
