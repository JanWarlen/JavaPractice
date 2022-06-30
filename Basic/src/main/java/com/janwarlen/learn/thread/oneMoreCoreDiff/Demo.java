package com.janwarlen.learn.thread.oneMoreCoreDiff;

import java.util.concurrent.atomic.AtomicLong;

public class Demo {

    public static void func(int num) {
        AtomicLong cost = new AtomicLong(0);
        Thread[] threads = new Thread[num];
        for (int j = 0; j < num; j++) {
            threads[j] = new Thread(() -> {
                long begin = System.currentTimeMillis();
                for (int i = 0; i < 1_000_000; i++) {
                    double v = Math.random() + Math.random() + Math.random() + Math.random() + Math.random() + Math.random();
                }
                long end = System.currentTimeMillis();
                cost.addAndGet(end - begin);
            });
            threads[j].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("总耗时：" + cost.get());
    }

    public static void main(String[] args) {
        // 多核场景：总耗时：126061
        // 期待一个单核场景结果...
        func(10);
    }
}
