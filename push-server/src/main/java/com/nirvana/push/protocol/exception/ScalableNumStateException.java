package com.nirvana.push.protocol.exception;

/**
 * RemainLength创建异常。
 * Created by Nirvana on 2017/8/10.
 */
public class ScalableNumStateException extends ProtocolException {

    private static final String MESSAGE = "变长字节状态异常。";

    public ScalableNumStateException() {
        super(MESSAGE);
    }

    public ScalableNumStateException(String message) {
        super(message);
    }

}
