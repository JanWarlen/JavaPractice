package com.janwarlen.learn.netty.file;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileTrans {

    public static void main(String[] args) throws IOException {
        // 零拷贝传输文件
        // 零拷贝指的是从文件系统到网络栈之间无拷贝操作（无须拷贝一份到JVM中）
        File file = new File("");
        FileInputStream fileInputStream = new FileInputStream(file);
        DefaultFileRegion fileRegion = new DefaultFileRegion(fileInputStream.getChannel(), 0, file.length());
        Channel channel = new NioServerSocketChannel();
        channel.writeAndFlush(fileRegion).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {

                }
            }
        });
    }
}
