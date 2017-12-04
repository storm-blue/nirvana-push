package com.nirvana.push.exception;

/**
 * 重写fillInStackTrace方法，作为性能更好的轻量级流程控制使用。
 * Created by Nirvana on 2017/8/5.
 */
public class StacklessException extends RuntimeException {

    protected boolean fillStack = false;

    public StacklessException() {
        this(false);
    }

    public StacklessException(boolean fillStack) {
        super();
        this.fillStack = fillStack;
    }

    public StacklessException(boolean fillStack, String message) {
        super(message);
        this.fillStack = fillStack;
    }

    @Override
    public Throwable fillInStackTrace() {
        if (fillStack) {
            return super.fillInStackTrace();
        } else {
            return this;
        }
    }
}
