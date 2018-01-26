package com.nirvana.xin.codec;

/**
 * Created by Nirvana on 2017/9/11.
 */
public class L2ProtocolException extends ProtocolException {

    private static final String MESSAGE = "L2 protocol exception.";

    public L2ProtocolException() {
        super(MESSAGE);
    }

    public L2ProtocolException(String message) {
        super(message);
    }

}
