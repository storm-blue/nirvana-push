package com.nirvana.push.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 可输出数组类。使用<code>CompositeByteBuf</code>的特性来组合子元素，提供输出服务。
 *
 * @author Nirvana
 */
public abstract class AbstractOutputableArray extends AbstactOutputable {

    private List<Outputable> elements = new ArrayList<>();

    /**
     * 组合ByteBuf.
     */
    private CompositeByteBuf byteBuf = Unpooled.compositeBuffer();

    public AbstractOutputableArray() {
    }

    public AbstractOutputableArray(Outputable... outputables) {
        for (Outputable outputable : outputables) {
            addElement(outputable);
        }
    }

    public AbstractOutputableArray(Collection<Outputable> outputables) {
        for (Outputable outputable : outputables) {
            addElement(outputable);
        }
    }

    /**
     * 增加组件。
     */
    protected void addElement(Outputable element) {
        elements.add(element);
        byteBuf.addComponent(true, element.getByteBuf());
    }

    /**
     * 子元素数量。
     */
    public int arraySize() {
        return elements.size();
    }

    /**
     * 清除组件。
     */
    public void clear() {
        elements.clear();
        byteBuf.clear();
    }

    @Override
    public ByteBuf getByteBuf() {
        return byteBuf;
    }
}
