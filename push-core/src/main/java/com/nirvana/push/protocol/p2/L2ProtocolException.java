package com.nirvana.push.protocol.p2;

import com.nirvana.push.protocol.exception.ProtocolException;

/**
 * 二级协议异常。
 * Created by Nirvana on 2017/9/11.
 */
public class L2ProtocolException extends ProtocolException {

    private static final String MESSAGE = "二级协议异常。";

    public L2ProtocolException() {
        super(MESSAGE);
    }

    public L2ProtocolException(String message) {
        super(message);
    }

}
