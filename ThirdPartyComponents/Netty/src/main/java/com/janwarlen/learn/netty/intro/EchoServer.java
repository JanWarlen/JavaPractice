package com.janwarlen.learn.netty.intro;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EchoServerHandler echoServerHandler = new EchoServerHandler();
        // 接收和处理新的连接
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventExecutors)
                    // 指定NIO传输的Channel
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 当一个新的连接被接受时，会创建一个新的子Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //将 EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeLine 中
                            ch.pipeline().addLast(echoServerHandler);
                        }
                    });
            // 绑定服务器，sync是阻塞，等待绑定完成
            ChannelFuture sync = serverBootstrap.bind().sync();
            // 阻塞，等到服务器的 Channel 关闭
            sync.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 80;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start();
    }
}
