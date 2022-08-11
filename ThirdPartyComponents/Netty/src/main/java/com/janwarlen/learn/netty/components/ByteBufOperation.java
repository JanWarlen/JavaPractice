package com.janwarlen.learn.netty.components;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.nio.charset.StandardCharsets;

public class ByteBufOperation {

    /**
     * slice出来的是类似于引用的，并没有额外分配空间存储，实际存储空间是公用的
     */
    public static void slice() {
        ByteBuf origin = Unpooled.copiedBuffer("hello world!".getBytes(StandardCharsets.UTF_8));
        ByteBuf slice = origin.slice(0, 5);
        System.out.println("slice: " + slice.toString(StandardCharsets.UTF_8));
        slice.setByte(0, 'y');
        System.out.println("slice.getByte(0) == origin.getByte(0): " + (slice.getByte(0) == origin.getByte(0)));
    }

    /**
     * copy是单独创建了一个实例存储数据，两者之间互不影响
     */
    public static void copy() {
        ByteBuf origin = Unpooled.copiedBuffer("hello world!".getBytes(StandardCharsets.UTF_8));
        ByteBuf copy = origin.copy(0, 5);
        System.out.println("copy: " + copy.toString(StandardCharsets.UTF_8));
        copy.setByte(0, 'y');
        System.out.println("copy.getByte(0) == origin.getByte(0): " + (copy.getByte(0) == origin.getByte(0)));
    }

    /**
     * 通过索引访问不影响 readerIndex 移动
     */
    public static void getAndSet() {
        ByteBuf origin = Unpooled.copiedBuffer("hello world!".getBytes(StandardCharsets.UTF_8));
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
        System.out.println("char at index 0: " + (char) origin.getByte(0));
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
        origin.setByte(0, 'y');
        System.out.println("char at index 0: " + (char) origin.getByte(0));
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
    }

    public static void readAndWrite() {
        ByteBuf origin = Unpooled.copiedBuffer("hello world!".getBytes(StandardCharsets.UTF_8));
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
        System.out.println("readByte: " + (char) origin.readByte());
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
        try {
            origin.writeByte('!');
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        // 释放已读取部分空间，新内容才可写入
        origin.discardReadBytes();
        origin.writeByte('!');
        System.out.println("new content: " + origin.toString(StandardCharsets.UTF_8));
        System.out.println("readerIndex:" + origin.readerIndex());
        System.out.println("writerIndex:" + origin.writerIndex());
    }

    /**
     * 直接内存即堆外内存
     * 使用直接内存可以减少一次写入时的拷贝
     * JVM会将堆内存Buffer拷贝一份到直接内存中，然后才能写入Socket中。JVM堆内存的数据是不能直接写入Socket中的
     */
    public static void allocate() {
        // 不池化 ByteBuf 实例，每次调用返回一个新的实例
        UnpooledByteBufAllocator unpooledByteBufAllocator = new UnpooledByteBufAllocator(true);
        // 池化 ByteBuf 实例，以提高性能和最大限度减少内存碎片
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator();
        unpooledByteBufAllocator.buffer();
        // 基于堆内存创建 ByteBuf 实例
        unpooledByteBufAllocator.heapBuffer();
        // 基于直接内存创建 ByteBuf 实例
        unpooledByteBufAllocator.directBuffer();
    }

    public static void main(String[] args) {
//        slice();
//        copy();
//        getAndSet();
        readAndWrite();
    }
}
