package com.nirvana.push.core.agent.exception;

import com.nirvana.push.exception.NoStackException;

/**
 * Created by Nirvana on 2017/9/6.
 */
public class CommunicationException extends NoStackException {

    private static final String MESSAGE = "Communicate Exception.";

    public CommunicationException() {
        super(MESSAGE);
    }

    public CommunicationException(String message) {
        super(message);
    }

}
