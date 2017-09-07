package com.nirvana.push.protocol;

import com.nirvana.push.utils.Assert;

/**
 * 基础协议包。
 * Created by Nirvana on 2017/8/9.
 */
public class BasePackage extends OutputableArray {

    /*包的剩余字节标志最大长度*/
    private static final int MAX_LR_PART_SIZE = 4;

    /*包的最大长度*/
    public static final int MAX_SIZE = (int) (HeaderPart.HEADER_PART_SIZE + MAX_LR_PART_SIZE + ScalableNumberPart.MAX_VALUES[MAX_LR_PART_SIZE - 1]);

    private HeaderPart header;

    private ScalableNumberPart remainLength;

    private ScalableNumberPart identifier;

    private final PayloadPart payload;

    public BasePackage(PackageType type, PackageLevel level, boolean retain, Long identifier, PayloadPart payload) {

        Assert.notNull(payload, "负载不能为空。");

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

    public BasePackage(HeaderPart header, ScalableNumberPart remainLength, ScalableNumberPart identifier, PayloadPart payload) {
        Assert.notNull(header, "头部不能为空。");
        Assert.notNull(remainLength, "剩余长度不能为空。");
        Assert.notNull(payload, "负载不能为空。");
        this.header = header;
        this.remainLength = remainLength;
        this.identifier = identifier;
        this.payload = payload;

        addElements(header, remainLength, identifier, payload);
    }

    public PackageType getPackageType() {
        return header.getPackageType();
    }

    public PackageLevel getPackageLevel() {
        return header.getPackageLevel();
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
        return "BasePackage{" +
                "header=" + header +
                ", remainLength=" + remainLength +
                ", identifier=" + identifier +
                ", payload=" + payload +
                '}';
    }
}
