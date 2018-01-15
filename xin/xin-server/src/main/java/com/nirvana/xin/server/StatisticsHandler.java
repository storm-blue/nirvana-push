package com.nirvana.xin.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Nirvana on 2017/8/1.
 */
@ChannelHandler.Sharable
public class StatisticsHandler extends ChannelOutboundHandlerAdapter {

    private static AtomicLong atomicLong = new AtomicLong(0);

    private static volatile long lastTime = System.currentTimeMillis();

    private static volatile long lastCount = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        long value = atomicLong.incrementAndGet();
        long now = System.currentTimeMillis();
        long over = now - lastTime;
        if (over > 5000) {
            System.out.println("已推送消息数量：" + value);
            System.out.println("实时每秒吞吐量：" + ((double) (value - lastCount) / over) * 1000);
            lastTime = now;
            lastCount = value;
        }
    }
}
