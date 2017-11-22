package com.nirvana.push.protocol.exception;

/**
 * RemainLength创建异常。
 * Created by Nirvana on 2017/8/10.
 */
public class ScalableNumCreateException extends ProtocolException {

    private static final String MESSAGE = "变长字节编码异常。";

    public ScalableNumCreateException() {
        super(MESSAGE);
    }

    public ScalableNumCreateException(String message) {
        super(message);
    }

}
