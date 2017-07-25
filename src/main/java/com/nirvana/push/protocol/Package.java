package com.nirvana.push.protocol;

import java.io.UnsupportedEncodingException;

/**
 * 协议包。提供获取包信息操作。
 */
public class Package extends ByteableArray {

    private Header header;

    private Body body;

    private Footer footer;

    public Package(MessageType type, MessageLevel level, MessageCharset charset, String content) throws UnsupportedEncodingException {
        body = new Body(content, charset.getCharset());
        header = new Header(type, level, charset, body.size());
        footer = Footer.getFooter();
    }

}
