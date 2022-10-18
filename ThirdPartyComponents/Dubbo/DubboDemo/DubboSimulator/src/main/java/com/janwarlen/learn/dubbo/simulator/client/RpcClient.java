package com.janwarlen.learn.dubbo.simulator.client;

import com.janwarlen.learn.dubbo.simulator.common.FutureMapUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class RpcClient {

    private volatile Channel channel;
    private Bootstrap bootstrap;

    public RpcClient() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        // 设置分隔符
                        ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes(StandardCharsets.UTF_8));
                        p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        // 业务处理handler
                        p.addLast(nettyClientHandler);
                    }
                });
        try {
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 9898).sync();
            if (sync.isDone() && sync.isSuccess()) {
                channel = sync.channel();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static final AtomicLong INVOKE_ID = new AtomicLong(100_000_000_000L);

    public CompletableFuture<String> rpcAsyncCall (String msg) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String reqId = String.valueOf(INVOKE_ID.getAndIncrement());
        String s = generatorFrame(msg, reqId);
        this.sendMsg(s);
        FutureMapUtil.put(reqId, future);
        return future;
    }

    public String rpcSyncCall (String msg) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String reqId = String.valueOf(INVOKE_ID.getAndIncrement());
        String s = generatorFrame(msg, reqId);
        this.sendMsg(s);
        FutureMapUtil.put(reqId, future);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMsg(String s) {
        channel.writeAndFlush(s);
    }

    private String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    public void close() {
        if (null != bootstrap) {
            bootstrap.config().group().shutdownGracefully();
        }
        if (null != channel) {
            channel.close();
        }
    }
}
