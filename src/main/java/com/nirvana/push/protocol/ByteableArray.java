package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class ByteableArray implements Outputter {

    private List<Byteable> elements = new ArrayList<>();

    public void output(ChannelHandlerContext context) {
        for (Byteable byteable : elements) {
        }
    }

    protected void addElement(Byteable element) {
        elements.add(element);
    }

    public int size() {
        return elements.size();
    }

    /**
     * remove all elements.
     */
    public void clear() {
        elements.clear();
    }

}
