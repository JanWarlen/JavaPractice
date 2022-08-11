package com.janwarlen.learn.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIODemo {

    public static void demo() throws IOException {
        try (RandomAccessFile aFile = new RandomAccessFile("Basic/src/main/resources/test", "rw")) {
            // 文件的IO操作通过 RandomAccessFile/FileInputStream/FileOutputStream 获取 Channel
            // 网络操作通过 ServerSocket/Socket 获取 Channel
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                // 读写模式切换需要调用 flip
                buf.flip();
                // 当 flip 切换后，写->读，position固定都会重置为0，因此每次切换到读取模式
                // 都建议一次性的将之前写入的内容全部读取避免遗漏
                while (buf.hasRemaining()) {
                    // 此方式无法支持中文
                    System.out.print((char) buf.get());
                }
                // 读取结束，清除已读取内容，释放空间给下一次读取
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        }
    }

    public static void buffer() throws IOException {
        try (RandomAccessFile aFile = new RandomAccessFile("Basic/src/main/resources/test", "rw")) {
            FileChannel inChannel = aFile.getChannel();
            //create buffer with capacity of 48 bytes
            ByteBuffer buf = ByteBuffer.allocate(48);
            while (inChannel.read(buf) != -1) {
                buf.flip();  //make buffer ready for read
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get()); // read 1 byte at a time
                }
                buf.clear(); //make buffer ready for writing
            }
            byte[] msg = "\nthis is from buffer".getBytes(StandardCharsets.UTF_8);
            ByteBuffer msgBuffer = ByteBuffer.allocate(msg.length);
            msgBuffer.put(msg);
            // 在将buffer写入文件之前，需要先将buffer flip从写模式转换为读模式
            msgBuffer.flip();
            int write = inChannel.write(msgBuffer);
            System.out.println(write == msg.length);
        }
    }

    /**
     * 使用 CharsetDecoder 对中文字节码进行转码，避免输出乱码
     */
    public static void bufferCN() throws IOException {
        try (RandomAccessFile aFile = new RandomAccessFile("Basic/src/main/resources/testCN", "rw")) {
            FileChannel inChannel = aFile.getChannel();
            //create buffer with capacity of 48 bytes
            ByteBuffer buf = ByteBuffer.allocate(48);
            CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
            CharBuffer charBuffer = CharBuffer.allocate(48);
            while (inChannel.read(buf) != -1) {
                buf.flip();  //make buffer ready for read
                charsetDecoder.decode(buf, charBuffer, true);
                charBuffer.flip();
                while (charBuffer.hasRemaining()) {
                    System.out.print(charBuffer.get());
                }
                // 中文一个汉字占据2+byte，因此不能再使用clear直接清理
                buf.compact();
                /*if (buf.hasRemaining()) {
                    // 此处代码可注释，仅表示会有少量byte无法转码
                    System.out.println();
                }*/
                charBuffer.clear();
                charBuffer.reset();
            }
        }
    }

    public static void channel() throws IOException {
        {
            RandomAccessFile fromFile = new RandomAccessFile("Basic/src/main/resources/testFrom", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("Basic/src/main/resources/testTo", "rw");
            FileChannel toChannel = toFile.getChannel();
            long position = toChannel.size();
            long count = fromChannel.size();
            // position 是 to 保存的起始索引
            toChannel.transferFrom(fromChannel, position, count);
        }
        {
            RandomAccessFile fromFile = new RandomAccessFile("Basic/src/main/resources/testFrom", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("Basic/src/main/resources/testTo", "rw");
            FileChannel toChannel = toFile.getChannel();
            long position = 0;
            long count = fromChannel.size();
            // position 是从 from 开始传输的起始索引
            fromChannel.transferTo(position, count, toChannel);
        }
    }

    public static void selector() throws IOException {
        // 创建一个server
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 创建一个 selector
        Selector selector = Selector.open();
        // 如果是 blocking 则会在注册时抛出异常 IllegalBlockingModeException
        channel.configureBlocking(false);
        // 向 channel 注册 selector，监听事件是 OP_READ
        // 监听范围可以通过 ｜ 叠加，如：SelectionKey.OP_READ | SelectionKey.OP_WRITE
        channel.register(selector, SelectionKey.OP_READ);
        while (true) {
            // selectNow 非阻塞，立即返回，select 会阻塞直到至少有一个 channel ready
            int readyChannels = selector.selectNow();
            if (readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 获取响应的 channel
                SelectableChannel opChannel = key.channel();
                // 附加对象，可以自由的追加
                Object attachment = key.attachment();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.

                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.

                } else if (key.isReadable()) {
                    // a channel is ready for reading

                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                // 处理完毕需要 remove， 再次触发时会自动再次加入
                keyIterator.remove();
            }
        }
    }

    public static void serverSocket() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听 9999 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            // 接受客户端连接，如果是 non-blocking 模式，则会立即返回 null 如果没有客户端连接的话
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                //do something with socketChannel...
            }
        }
    }

    /**
     * UDP 报文连接方式
     */
    public static void datagramChannel() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        ByteBuffer msgReceive = ByteBuffer.allocate(48);
        msgReceive.clear();
        channel.receive(msgReceive);

        String newData = "New String to write to file..."
                + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));

        channel.connect(new InetSocketAddress("jenkov.com", 80));
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int bytesRead = channel.read(buffer);
        int bytesWritten = channel.write(buffer);
    }

    /**
     * 两个线程之间的单向数据连接。
     * Thread-A -> Pipe[SinkChannel -> SourceChannel ] -> Thread-B
     * @throws IOException
     */
    public static void pipe() throws IOException {
        // 创建
        Pipe pipe = Pipe.open();
        // 向 pipe 中写入数据
        {
            Pipe.SinkChannel sinkChannel = pipe.sink();
            String newData = "New String to write to file..." + System.currentTimeMillis();
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put(newData.getBytes());
            buf.flip();
            while (buf.hasRemaining()) {
                sinkChannel.write(buf);
            }
        }
        // 从 pipe 中读取数据
        {
            Pipe.SourceChannel sourceChannel = pipe.source();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = sourceChannel.read(buf);
        }
    }

    public static void main(String[] args) throws IOException {
//        channel();
//        buffer();
//        bufferCN();
        channel();
    }
}
