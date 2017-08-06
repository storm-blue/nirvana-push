package com.nirvana.push.protocol.exception;

public class DecoderInitException extends ChannelHandlerInitException {

    private static final String MESSAGE = "解码器初始化异常。";

    public DecoderInitException() {
        super(MESSAGE);
    }

    public DecoderInitException(String message) {
        super(message);
    }

}
