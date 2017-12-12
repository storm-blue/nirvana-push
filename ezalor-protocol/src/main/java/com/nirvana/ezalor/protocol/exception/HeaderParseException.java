package com.nirvana.ezalor.protocol.exception;

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
