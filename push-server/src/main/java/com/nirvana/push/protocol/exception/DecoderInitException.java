package com.nirvana.push.protocol.exception;

/**
 * 解码器初始化异常。
 * Created by Nirvana on 2017/8/5.
 */
public class DecoderInitException extends ChannelHandlerInitException {

    private static final String MESSAGE = "解码器初始化异常。";

    public DecoderInitException() {
        super(MESSAGE);
    }

    public DecoderInitException(String message) {
        super(message);
    }

}
