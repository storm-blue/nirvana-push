package com.nirvana.push.core.agent.exception;

import com.nirvana.push.exception.NoStackException;

/**
 * Created by Nirvana on 2017/9/6.
 */
public class ConnectException extends NoStackException {

    private static final String MESSAGE = "Connect异常。";

    public ConnectException() {
        super(MESSAGE);
    }

    public ConnectException(String message) {
        super(message);
    }

}
