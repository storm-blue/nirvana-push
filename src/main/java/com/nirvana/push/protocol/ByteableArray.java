package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ByteableArray implements Outputter {

    private List<Byteable> elements = new ArrayList<>();

    public void output(ChannelHandlerContext context) {
        for (Byteable byteable : elements) {
            context.writeAndFlush(byteable.getBytes());
        }
    }

    @Override
    public void output(OutputStream outputStream) throws IOException {
        for (Byteable byteable : elements) {
            outputStream.write(byteable.getBytes());
        }
        outputStream.flush();
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
