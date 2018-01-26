package com.nirvana.xin.codec.netty.exception;

import com.nirvana.xin.codec.ProtocolException;

/**
 * Created by Nirvana on 2017/8/10.
 */
public class ScalableNumStateException extends ProtocolException {

    private static final String MESSAGE = "Scalable number state exception.";

    public ScalableNumStateException() {
        super(MESSAGE);
    }

    public ScalableNumStateException(String message) {
        super(message);
    }

}
