package com.nirvana.push.protocol.exception;

import com.nirvana.push.exception.NoStackException;

/**
 * 包解析异常。
 * Created by Nirvana on 2017/8/5.
 */
public class PackageParseException extends NoStackException {

    private static final String MESSAGE = "基础包解析错误。";

    public PackageParseException() {
        super(MESSAGE);
    }

    public PackageParseException(String message) {
        super(message);
    }
}
