package com.nirvana.push.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class ByteableArray implements Outputable {

    private List<Byteable> byteables = new ArrayList<>();

    public void output(ChannelHandlerContext context) {
        for (Byteable byteable : byteables) {
        }
    }
}
