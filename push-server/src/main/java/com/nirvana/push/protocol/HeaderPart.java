package com.nirvana.push.protocol;

import com.nirvana.push.protocol.exception.HeaderParseException;
import com.nirvana.push.utils.BitRuler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * com.nirvana.push.protocol.BaseHeader.java.
 * <p>
 * Created by Nirvana on 2017/8/9.
 */
public class HeaderPart implements Outputable {

    private ByteBuf data;

    //包类型
    private PackageType packageType;

    //包的传输等级
    private PackageLevel packageLevel;

    //是否保留
    private boolean retain;

    //是否有序
    private boolean sequential;

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
        if (buf.readableBytes() != 1) {
            throw new HeaderParseException("消息头部分长度必须为1");
        }
        this.data = buf;
        byte b = buf.getByte(0);
        packageType = PackageType.get(BitRuler.r(b, 5, 8));
        sequential = BitRuler.r(b, 4, 4) == 1;
        packageLevel = PackageLevel.get(BitRuler.r(b, 2, 3));
        retain = BitRuler.r(b, 1, 1) == 1;
    }

    public HeaderPart(PackageType type, PackageLevel level, boolean sequential, boolean retain) {
        byte b = (byte) (type.getCode() << 4 | (sequential ? 1 : 0) << 3 | (level.getCode() << 1) | (retain ? 1 : 0));
        data = Unpooled.wrappedBuffer(new byte[]{b});
        this.packageType = type;
        this.packageLevel = level;
        this.sequential = sequential;
        this.retain = retain;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public PackageLevel getPackageLevel() {
        return packageLevel;
    }

    public boolean isRetain() {
        return retain;
    }

    public boolean isSequential() {
        return sequential;
    }

    @Override
    public ByteBuf getByteBuf() {
        return data;
    }

    @Override
    public String toString() {
        return "HeaderPart{" +
                "packageType=" + packageType +
                ", packageLevel=" + packageLevel +
                ", retain=" + retain +
                ", sequential=" + sequential +
                '}';
    }
}
