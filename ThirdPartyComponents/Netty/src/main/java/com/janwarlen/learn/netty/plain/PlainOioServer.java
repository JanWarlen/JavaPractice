package com.janwarlen.learn.netty.plain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlainOioServer {

    public void serve(int port) throws IOException {
        // 注册端口
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                // 阻塞等待客户端连接
                Socket accept = serverSocket.accept();
                System.out.println("accept socket from: " + accept);
                // 另起线程处理客户端，便于快速循环接收下一个连接
                new Thread(() -> {
                    try (OutputStream out = accept.getOutputStream()) {
                        out.write("Hello\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();
                        accept.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
