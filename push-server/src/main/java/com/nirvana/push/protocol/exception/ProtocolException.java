package com.nirvana.push.protocol.exception;

import com.nirvana.push.exception.NoStackException;

/**
 * 协议异常。
 * Created by Nirvana on 2017/8/5.
 */
public class ProtocolException extends NoStackException {

    private static final String MESSAGE = "协议异常。";

    public ProtocolException() {
        super(MESSAGE);
    }

    public ProtocolException(String message) {
        super(message);
    }
}
