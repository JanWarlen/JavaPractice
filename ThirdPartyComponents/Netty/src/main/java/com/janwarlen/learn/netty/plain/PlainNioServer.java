package com.janwarlen.learn.netty.plain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {

    public void serve(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        socket.bind(inetSocketAddress);
        Selector selector = Selector.open();
        // serverSocketChannel 注册 selector 监听 socket连接接受事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        while (true) {
            // 阻塞等待需要处理的事件
            selector.select();
            // 接收事件的所有 SelectionKey 实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) next.channel();
                    try (SocketChannel client = server.accept()) {
                        client.configureBlocking(false);
                        // client 注册 selector 监听读写事件
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("accept from: " + client);
                        if (next.isWritable()) {
                            // 是否已准备好写入数据
                            SocketChannel channel = (SocketChannel) next.channel();
                            ByteBuffer attachment = (ByteBuffer) next.attachment();
                            while (attachment.hasRemaining()) {
                                // 函数返回的是写入的字节数
                                if (client.write(attachment) == 0) {
                                    // 数据已写入客户端
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
