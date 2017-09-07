package com.nirvana.push.exception;

/**
 * 重写fillInStackTrace方法，作为性能更好的轻量级流程控制使用。
 * Created by Nirvana on 2017/8/5.
 */
public class NoStackException extends RuntimeException {

    private static boolean fill = true;

    public NoStackException() {
        super();
    }

    public NoStackException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        if (fill) {
            return super.fillInStackTrace();
        } else {
            return this;
        }
    }
}
