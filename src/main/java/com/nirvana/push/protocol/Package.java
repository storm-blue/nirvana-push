package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 协议包。提供获取包信息操作。
 */
public class Package extends OutputableArray {

    /**
     * 包最大长度。
     */
    public static int MAX_LENGTH = 65535 + Header.HEADER_SIZE + Footer.FOOT_SIZE;

    private Header header;

    private Body body;

    private Footer footer;

    public Package(ByteBuf byteBuf) {
        header = new Header(byteBuf.slice(0, Header.HEADER_SIZE));
        body = new Body(byteBuf.slice(Header.HEADER_SIZE, header.getPayloadSize()), header.getMessageCharset().getCharset());
        footer = Footer.getFooter();
        addElement(header);
        addElement(body);
        addElement(footer);
    }

    public Package(Header header, Body body, Footer footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
        addElement(header);
        addElement(body);
        addElement(footer);
    }

    public Package(byte[] bytes) {
        this(Unpooled.wrappedBuffer(bytes));
    }

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
        header = new Header(type, level, charset, body.getSize());
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

    public int contentSize() {
        return header.getPayloadSize();
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public Footer getFooter() {
        return footer;
    }

    @Override
    public String toString() {
        return "Package{" +
                "header=" + header +
                ", body=" + body +
                ", footer=" + footer +
                '}';
    }
}
