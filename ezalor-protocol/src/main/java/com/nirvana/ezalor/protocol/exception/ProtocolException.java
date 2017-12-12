package com.nirvana.ezalor.protocol.exception;

/**
 * Created by Nirvana on 2017/8/5.
 */
public class ProtocolException extends RuntimeException {

    private static final String MESSAGE = "Protocol exception.";

    public ProtocolException() {
        super(MESSAGE);
    }

    public ProtocolException(String message) {
        super(message);
    }
}
