package com.nirvana.push.exception;

/**
 * 重写fillInStackTrace方法，作为性能更好的轻量级流程控制使用。
 */
public class NoStackException extends RuntimeException {

    public NoStackException() {
        super();
    }

    public NoStackException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
