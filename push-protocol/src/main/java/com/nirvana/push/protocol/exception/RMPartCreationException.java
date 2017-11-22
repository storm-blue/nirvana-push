package com.nirvana.push.protocol.exception;

/**
 * RemainLength创建异常。
 * Created by Nirvana on 2017/8/10.
 */
public class RMPartCreationException extends ProtocolException {

    private static final String MESSAGE = "RemainLength创建异常。";

    public RMPartCreationException() {
        super(MESSAGE);
    }

    public RMPartCreationException(String message) {
        super(message);
    }

}
