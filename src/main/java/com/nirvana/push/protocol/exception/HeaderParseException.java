package com.nirvana.push.protocol.exception;

/**
 * 包头解析异常。
 * Created by Nirvana on 2017/8/5.
 */
public class HeaderParseException extends PackageParseException {

    private static final String MESSAGE = "Header解析错误。";

    public HeaderParseException() {
        super(MESSAGE);
    }

    public HeaderParseException(String message) {
        super(message);
    }

}
