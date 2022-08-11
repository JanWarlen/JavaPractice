package com.janwarlen.learn.netty.udp;

import com.janwarlen.learn.netty.websocket.HttpRequestHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;

public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws InterruptedException, IOException {
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        while (true) {
            long length = file.length();
            if (length < pointer) {
                // 文件内容有被删除
                pointer = length;
            } else if (length > pointer) {
                // 文件有新增内容
                RandomAccessFile r = new RandomAccessFile(file, "r");
                r.seek(pointer);
                String line;
                while ((line = r.readLine()) != null) {
                    System.out.println("new msg:" + line);
                    ChannelFuture channelFuture = channel.writeAndFlush(new LogEvent(file.getAbsolutePath(), line));
                    channelFuture.addListener((ChannelFutureListener) future -> {
                        System.out.println("write res: " + future.isSuccess());
                        if (!future.isSuccess()) {
                            future.cause().printStackTrace();
                        }
                    });
                }
                pointer = r.getFilePointer();
                r.close();
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                System.out.println("err");
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        File logFile = null;
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "log";
            path = !path.contains("file:") ? path : path.substring(5);
            logFile = new File(path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LogEventBroadcaster logEventBroadcaster = new LogEventBroadcaster(new InetSocketAddress("255.255.255.255", 9997), logFile);
        try {
            logEventBroadcaster.run();
        } finally {
            logEventBroadcaster.stop();
        }
    }
}
