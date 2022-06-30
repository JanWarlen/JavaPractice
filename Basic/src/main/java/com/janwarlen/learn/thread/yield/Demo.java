package com.janwarlen.learn.thread.yield;

public class Demo {

    public static void func() {
        new Thread(() -> {
            long begin = System.currentTimeMillis();
            for (int i = 0, sum = 0; i < 5_000_000; i++) {
//                Thread.yield();
                sum += i;
            }
            long end = System.currentTimeMillis();
            System.out.println("cost time:" + (end - begin));
        }).start();
    }

    public static void main(String[] args) {
        func();
    }
}
