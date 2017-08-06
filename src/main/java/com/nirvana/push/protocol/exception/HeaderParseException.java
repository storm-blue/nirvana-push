package com.nirvana.push.protocol.exception;

public class HeaderParseException extends PackageParseException {

    private static final String MESSAGE = "Header解析错误。";

    public HeaderParseException() {
        super(MESSAGE);
    }

    public HeaderParseException(String message) {
        super(message);
    }

}
