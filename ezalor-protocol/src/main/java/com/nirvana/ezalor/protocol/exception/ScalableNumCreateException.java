package com.nirvana.ezalor.protocol.exception;

/**
 * Created by Nirvana on 2017/8/10.
 */
public class ScalableNumCreateException extends ProtocolException {

    private static final String MESSAGE = "Scalable number create exception.";

    public ScalableNumCreateException() {
        super(MESSAGE);
    }

    public ScalableNumCreateException(String message) {
        super(message);
    }

}
