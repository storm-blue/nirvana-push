package com.nirvana.ezalor.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 协议变长字节数值编解码器。
 * Created by Nirvana on 2017/8/10.
 */
public class ScalableNumUtils {

    public static byte[] getBytes(long number, int maxBytes) {
        byte[] bytes = new byte[maxBytes];
        int i = fillToBytes(number, bytes);
        byte[] output = new byte[i];
        System.arraycopy(bytes, 0, output, 0, i);
        return output;
    }

    public static ByteBuf getByteBuf(long number, int maxBytes) {
        byte[] bytes = new byte[maxBytes];
        int i = fillToBytes(number, bytes);
        return Unpooled.wrappedBuffer(bytes, 0, i);
    }

    /**
     * 将数值填充至字节数组中。
     *
     * @param number 数值
     * @param bytes  byte[]
     */
    private static int fillToBytes(long number, byte[] bytes) {
        int i = 0;
        while (number > 0) {
            if (i > bytes.length - 1) {
                throw new IllegalArgumentException("数值超过范围");
            }
            bytes[i] = (byte) (number % 128);
            number = number / 128;
            if (number > 0) {
                bytes[i] = (byte) (bytes[i] | (byte) 128);
            }
            i++;
        }
        return i;
    }

}
