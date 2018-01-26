package com.nirvana.xin.codec.netty.exception;

import com.nirvana.xin.codec.ProtocolException;

/**
 * Created by Nirvana on 2017/8/5.
 */
public class HeaderParseException extends ProtocolException {

    private static final String MESSAGE = "Header parse exception.";

    public HeaderParseException() {
        super(MESSAGE);
    }

    public HeaderParseException(String message) {
        super(message);
    }

}
