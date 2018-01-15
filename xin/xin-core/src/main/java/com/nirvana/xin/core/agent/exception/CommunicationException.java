package com.nirvana.xin.core.agent.exception;

/**
 * Created by Nirvana on 2017/9/6.
 */
public class CommunicationException extends RuntimeException {

    private static final String MESSAGE = "Communicate Exception.";

    public CommunicationException() {
        super(MESSAGE);
    }

    public CommunicationException(String message) {
        super(message);
    }

}
