package com.janwarlen.learn.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        String sb = msg.getReceivedTimestamp() +
                "[" + msg.getSource().toString() + "] [ " +
                msg.getLogfile() + "] : " +
                msg.getMsg();
        System.out.println(sb);
    }
}
