package com.nirvana.ezalor.core;

/**
 * 销毁失败异常。
 * Created by Nirvana on 2017/9/7.
 */
public class DestroyFailedException extends Exception {

    private static final String message = "销毁失败";

    public DestroyFailedException() {
        super(message);
    }

    public DestroyFailedException(String msg) {
        super(msg);
    }

}
