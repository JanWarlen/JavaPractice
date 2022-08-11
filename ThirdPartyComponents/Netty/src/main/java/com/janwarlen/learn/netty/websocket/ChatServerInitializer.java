package com.janwarlen.learn.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 将字节码解码为 HttpRequest、HttpContext、LastHttpContent;并将 HttpRequest、HttpContext、LastHttpContent 编码为字节
        pipeline.addLast(new HttpServerCodec());
        // 写入一个文件的内容
        pipeline.addLast(new ChunkedWriteHandler());
        // 下一个 channelHandler 将只会收到完整的 HTTP 请求或响应
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        // 处理 FullHttpRequest
        pipeline.addLast(new HttpRequestHandler("/ws"));
        // 根据 WebSocket 规范，处理 WebSocket 升级握手、PingWebSocketFrame、PongWebSocketFrame、CloseWebSocketFrame
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 处理 TextWebSocketFrame 和 握手完成事件
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
