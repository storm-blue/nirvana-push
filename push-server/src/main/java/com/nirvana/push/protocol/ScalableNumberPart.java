package com.nirvana.push.protocol;

import com.nirvana.push.protocol.exception.ScalableNumCreateException;
import com.nirvana.push.protocol.exception.ScalableNumStateException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 可变字节编码。根据数值的不同会编码为不同长度的字节数组。最大数值为：72,057,594,037,927,935
 * 此类的对象有两种状态，一种是创建中的状态，一种是创建完成状态。对于其所包含的ByteBuf对象，只有创建结束之后才可见。
 * Created by Nirvana on 2017/8/9.
 */
public class ScalableNumberPart extends AbstractOutputable {

    //最大字节数
    public static final int MAX_BYTES = 8;

    //可编码的最大值
    public static final long MAX_VALUE = 72057594037927935L;

    /*可变字节的值*/
    private long value = 0;

    private ByteBuf buf;

    /**
     * @param number 要编码的数值。
     */
    public ScalableNumberPart(long number) {

        if (number > MAX_VALUE) {
            throw new ScalableNumCreateException("编码数值必须小于：" + MAX_VALUE);
        }
        value = number;

        byte[] bytes = new byte[MAX_BYTES];
        int i = 0;
        while (number > 0) {
            bytes[i] = (byte) (number % 128);
            number = number / 128;
            if (number > 0) {
                bytes[i] = (byte) (bytes[i] | (byte) 128);
            }
            i++;
        }

        buf = Unpooled.wrappedBuffer(bytes, 0, i);
        creationFinished = true;
    }

    private ScalableNumberPart() {
        bytes = new byte[MAX_BYTES];
    }

    /*创建工作是否完成。*/
    private boolean creationFinished = false;

    private long multiplier = 1;
    private int index = 0;
    private byte[] bytes;

    public static ScalableNumberPart startCreation() {
        return new ScalableNumberPart();
    }

    /**
     * 添加新的字节。
     *
     * @return 是否创建完毕
     */
    public boolean append(byte b) {
        if (creationFinished) {
            throw new ScalableNumCreateException("此RemainLengthPart已经创建完成，无法添加新的字节。");
        }
        bytes[index] = b;
        index++;
        value += (b & (byte) 127) * multiplier;

        //如果遇到字节最高位为0，则为终止字节，创建工作完成。
        if ((b & (byte) 128) == 0) {
            buf = Unpooled.wrappedBuffer(bytes, 0, index);
            creationFinished = true;
            return true;
        }

        multiplier *= 128;
        return false;
    }

    /**
     * 获取编码的值。
     */
    public long getValue() {
        return value;
    }

    /**
     * 是否创建完成。
     */
    public boolean isCreationFinished() {
        return creationFinished;
    }

    @Override
    public ByteBuf getByteBuf() {
        if (!creationFinished) {
            throw new ScalableNumStateException("此变长字节对象尚未创建完成");
        }
        return buf;
    }
}
