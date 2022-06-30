package com.janwarlen.learn.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {

    public static void func() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println("run: " + Thread.currentThread().getName()));
        }
        try {
            Thread.sleep(2_000);
            System.out.println("------------------");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println("run: " + Thread.currentThread().getName()));
        }
    }

    public static void funcSingleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println("run: " + Thread.currentThread().getName()));
        }
    }

    public static void main(String[] args) {
//        func();
        funcSingleThreadPool();
    }
}
