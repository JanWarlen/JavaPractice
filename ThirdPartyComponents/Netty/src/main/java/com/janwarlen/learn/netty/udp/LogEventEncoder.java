package com.janwarlen.learn.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress address;

    public LogEventEncoder(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        byte[] file = msg.getLogfile().getBytes(StandardCharsets.UTF_8);
        byte[] m = msg.getMsg().getBytes(StandardCharsets.UTF_8);
        ByteBuf buffer = ctx.alloc().buffer(file.length + m.length + 1);
        buffer.writeBytes(file);
        buffer.writeByte(LogEvent.SEPARATOR);
        buffer.writeBytes(m);
        out.add(new DatagramPacket(buffer, address));
    }
}
