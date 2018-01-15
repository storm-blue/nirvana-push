package com.nirvana.xin.protocol;

import com.nirvana.xin.core.message.*;
import com.nirvana.xin.core.message.Package;
import com.nirvana.xin.protocol.exception.ProtocolException;
import com.nirvana.xin.protocol.l2.DSTPackage;
import com.nirvana.xin.utils.Assert;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * 基础协议包。
 * Created by Nirvana on 2017/8/9.
 */
public class ProtocolPackage extends OutputableArray implements ProtocolObject {

    /*包的剩余字节标志最大长度*/
    private static final int MAX_LR_PART_SIZE = 4;

    /*包的最大长度*/
    public static final int MAX_SIZE = (int) (HeaderPart.HEADER_PART_SIZE + MAX_LR_PART_SIZE + ScalableNumberPart.MAX_VALUES[MAX_LR_PART_SIZE - 1]);

    private HeaderPart header;

    private ScalableNumberPart remainLength;

    private ScalableNumberPart identifier;

    private final PayloadPart payload;

    public static ProtocolPackage fromPackage(Package pkg) {

        Long identifier;

        Object id = pkg.getId();
        if (id == null) {
            identifier = null;
        } else {
            if ((id instanceof Long) || (id instanceof Integer)) {
                identifier = (long) id;
            } else {
                throw new ProtocolException("Unsupported identifier type: " + id.getClass().getSimpleName());
            }
        }

        MessageLevel messageLevel = pkg.getLevel();
        PackageType packageType = pkg.getType();
        boolean retain = pkg.isRetain();

        DSTPackage dstPackage = new DSTPackage(pkg);
        return new ProtocolPackage(packageType, messageLevel, retain, identifier, new PayloadPart(Unpooled.copiedBuffer(dstPackage.getContent(), Charset.forName("UTF-8"))));
    }

    private ProtocolPackage(PackageType type, MessageLevel level, boolean retain, Long identifier, PayloadPart payload) {

        Assert.notNull(payload, "payload must not be null.");

        boolean identifiable = identifier != null;

        this.header = new HeaderPart(type, level, identifiable, retain);
        addElement(header);

        //如果存在标识符，计算剩余总长度，添加子组件。
        if (identifiable) {
            Assert.notNull(identifier, "标识符不能为空。");
            this.identifier = new ScalableNumberPart(identifier);
            this.remainLength = new ScalableNumberPart(this.identifier.getSize() + payload.getSize());
            addElements(remainLength, this.identifier);
        }

        //不存在标识符，剩余总长度=payload的长度。
        else {
            this.remainLength = new ScalableNumberPart(payload.getSize());
            addElement(remainLength);
        }

        this.payload = payload;
        addElement(payload);
    }

    public ProtocolPackage(HeaderPart header, ScalableNumberPart remainLength, ScalableNumberPart identifier, PayloadPart payload) {
        Assert.notNull(header, "header part must not be null.");
        Assert.notNull(remainLength, "remain length part must not be null.");
        //Assert.notNull(payload, "payload must not be null.");
        this.header = header;
        this.remainLength = remainLength;
        this.identifier = identifier;
        this.payload = payload;

        addElements(header, remainLength, identifier, payload);
    }

    public PackageType getPackageType() {
        return header.getPackageType();
    }

    public MessageLevel getMessageLevel() {
        return header.getMessageLevel();
    }

    public boolean isRetain() {
        return header.isRetain();
    }

    public boolean isIdentifiable() {
        return header.isIdentifiable();
    }

    public Long getIdentifier() {
        return identifier == null ? null : identifier.getValue();
    }

    public PayloadPart getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "ProtocolPackage{" +
                "header=" + header +
                ", remainLength=" + remainLength +
                ", identifier=" + identifier +
                ", payload=" + payload +
                '}';
    }

    @Override
    public Package getPackage() {
        DSTPackage dstPackage = new DSTPackage(payload.byteBuf.toString(Charset.forName("UTF-8")));
        return new Package(getPackageType(), getMessageLevel(), getIdentifier(), isRetain(), dstPackage.getCardBox());
    }
}
