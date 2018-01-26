package com.nirvana.xin.codec.netty.exception;

import com.nirvana.xin.codec.ProtocolException;

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
