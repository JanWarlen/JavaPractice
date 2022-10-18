package com.janwarlen.learn.dubbo.simulator.client;

import com.janwarlen.learn.dubbo.simulator.common.FutureMapUtil;
import com.janwarlen.learn.dubbo.simulator.common.AllChannelHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CompletableFuture;

@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AllChannelHandler.channelRead(() -> {
            CompletableFuture future = FutureMapUtil.remove(((String) msg).split(":")[1]);
            if (null != future) {
                future.complete(((String) msg).split(":")[0]);
            }
        });
    }
}
