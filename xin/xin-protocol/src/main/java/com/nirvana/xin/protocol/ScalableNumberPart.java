package com.nirvana.xin.protocol;

import com.nirvana.xin.protocol.exception.ScalableNumStateException;
import com.nirvana.xin.protocol.exception.ScalableNumCreateException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可变字节编码。根据数值的不同会编码为不同长度的字节数组。最大数值为：72,057,594,037,927,935
 * 此类的对象有两种状态，一种是创建中的状态，一种是创建完成状态。对于其所包含的ByteBuf对象，只有创建结束之后才可见。
 * Created by Nirvana on 2017/8/9.
 */
public class ScalableNumberPart extends AbstractOutputable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScalableNumberPart.class);

    //最大字节数
    private static final int LONG_INT_MAX_BYTES = 8;

    //可编码的最大值
    static final long[] MAX_VALUES = {127L, 16383L, 2097151L, 268435455L, 34359738367L, 4398046511103L, 562949953421311L, 72057594037927935L};

    /*可变字节的值*/
    private long value = 0;

    private int maxBytes = LONG_INT_MAX_BYTES;

    /**
     * @param number 要编码的数值。
     */
    public ScalableNumberPart(long number) {
        this(number, LONG_INT_MAX_BYTES);
    }

    /**
     * @param number   要编码的数值。
     * @param maxBytes 最大字节数
     */
    public ScalableNumberPart(long number, int maxBytes) {

        this.maxBytes = maxBytes;

        if (number > MAX_VALUES[maxBytes - 1]) {
            throw new ScalableNumCreateException("编码数值必须小于：" + MAX_VALUES[maxBytes - 1]);
        }
        value = number;

        byte[] bytes = new byte[maxBytes];
        int i = 0;
        while (number > 0) {
            bytes[i] = (byte) (number % 128);
            number = number / 128;
            if (number > 0) {
                bytes[i] = (byte) (bytes[i] | (byte) 128);
            }
            i++;
        }

        byteBuf = Unpooled.wrappedBuffer(bytes, 0, i);
        creationFinished = true;
    }

    private ScalableNumberPart(int maxBytes) {
        bytes = new byte[LONG_INT_MAX_BYTES];
        this.maxBytes = maxBytes;
    }

    /*创建工作是否完成。*/
    private boolean creationFinished = false;

    private long multiplier = 1;
    private int index = 0;
    private byte[] bytes;

    public static ScalableNumberPart startCreation(int maxBytes) {
        return new ScalableNumberPart(maxBytes);
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
        if (index >= maxBytes) {
            throw new ScalableNumCreateException("超过了最大限制字节长度：" + maxBytes);
        }
        bytes[index] = b;
        index++;
        value += (b & (byte) 127) * multiplier;

        //如果遇到字节最高位为0，则为终止字节，创建工作完成。
        if ((b & (byte) 128) == 0) {
            byteBuf = Unpooled.wrappedBuffer(bytes, 0, index);
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
            throw new ScalableNumStateException("此变长字节对象尚未创建完成。");
        }
        return super.getByteBuf();
    }

    @Override
    public String toString() {
        return "ScalableNumberPart{" +
                "value=" + value +
                ", maxBytes=" + maxBytes +
                ", creationFinished=" + creationFinished +
                ", index=" + index +
                '}';
    }
}
