package com.nirvana.push.protocol.exception;

import com.nirvana.push.exception.NoStackException;

public class ChannelHandlerInitException extends NoStackException {

    private static final String MESSAGE = "ChannelHandler初始化异常。";

    public ChannelHandlerInitException() {
        super(MESSAGE);
    }

    public ChannelHandlerInitException(String message) {
        super(message);
    }

}
