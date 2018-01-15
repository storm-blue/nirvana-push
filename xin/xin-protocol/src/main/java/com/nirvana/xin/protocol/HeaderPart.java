package com.nirvana.xin.protocol;

import com.nirvana.xin.core.message.MessageLevel;
import com.nirvana.xin.core.message.PackageType;
import com.nirvana.xin.protocol.exception.HeaderParseException;
import com.nirvana.xin.utils.BitRuler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 基本消息头。
 * Created by Nirvana on 2017/8/9.
 */
public class HeaderPart extends AbstractOutputable {

    public static final int HEADER_PART_SIZE = 1;

    //包类型
    private PackageType packageType;

    //包的传输等级
    private MessageLevel packageLevel;

    //是否保留
    private boolean retain;

    //是否有序
    private boolean identifiable;

    public HeaderPart(byte b) {
        this(new byte[]{b});
    }

    public HeaderPart(byte[] bytes) {
        this(Unpooled.wrappedBuffer(bytes));
    }

    public HeaderPart(ByteBuf buf) {
        if (buf == null) {
            throw new HeaderParseException("参数不能为空");
        }
        if (buf.readableBytes() != HEADER_PART_SIZE) {
            throw new HeaderParseException("消息头部分长度必须为1");
        }
        this.byteBuf = buf;
        byte b = buf.getByte(0);

        packageType = PackageType.get(BitRuler.r(b, 5, 8));
        if (packageType == null) {
            throw new HeaderParseException("包类型解析错误");
        }

        identifiable = BitRuler.r(b, 4, 4) == 1;

        packageLevel = MessageLevel.get(BitRuler.r(b, 2, 3));
        if (packageLevel == null) {
            throw new HeaderParseException("包传输等级解析错误");
        }

        retain = BitRuler.r(b, 1, 1) == 1;
    }

    public HeaderPart(PackageType type, MessageLevel level, boolean identifiable, boolean retain) {
        byte b = (byte) (type.getCode() << 4 | (identifiable ? 1 : 0) << 3 | (level.getCode() << 1) | (retain ? 1 : 0));
        byteBuf = Unpooled.wrappedBuffer(new byte[]{b});
        this.packageType = type;
        this.packageLevel = level;
        this.identifiable = identifiable;
        this.retain = retain;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public MessageLevel getMessageLevel() {
        return packageLevel;
    }

    public boolean isRetain() {
        return retain;
    }

    public boolean isIdentifiable() {
        return identifiable;
    }

    @Override
    public String toString() {
        return "HeaderPart{" +
                "packageType=" + packageType +
                ", packageLevel=" + packageLevel +
                ", retain=" + retain +
                ", identifiable=" + identifiable +
                '}';
    }
}
