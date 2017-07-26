package com.nirvana.push.protocol;

/**
 * 协议包。提供获取包信息操作。
 */
public class Package extends ByteableArray {

    private Header header;

    private Body body;

    private Footer footer;

    public Package(MessageType type, String content) {
        this(type, MessageLevel.NO_CONFIRM, MessageCharset.UTF8, content);
    }

    public Package(MessageType type, MessageCharset charset, String content) {
        this(type, MessageLevel.NO_CONFIRM, charset, content);
    }

    public Package(MessageType type, MessageLevel level, String content) {
        this(type, level, MessageCharset.UTF8, content);
    }

    public Package(MessageType type, MessageLevel level, MessageCharset charset, String content) {
        body = new Body(content, charset.getCharset());
        header = new Header(type, level, charset, body.size());
        footer = Footer.getFooter();
        addElement(header);
        addElement(body);
        addElement(footer);
    }

    public String getContent() {
        return body.getContent();
    }

    public String getCharset() {
        return body.getCharset();
    }

    public int size() {
        return 0;
    }

    public int contentSize() {
        return 0;
    }

}
