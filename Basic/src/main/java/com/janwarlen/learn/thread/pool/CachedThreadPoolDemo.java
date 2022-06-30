package com.janwarlen.learn.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {

    /**
     * 执行结束后并未直接销毁，而是进入了 TIMED_WAITING 的状态
     */
    public static void func() {
        ExecutorService executorService = Executors.newCachedThreadPool();
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

    public static void funcCustomFactory() {
        ExecutorService executorService = Executors.newCachedThreadPool((r) -> {
            Thread thread = new Thread(r);
            // 设置为守护进程，这样 main 进程结束可以直接结束程序
            thread.setDaemon(true);
            thread.setName("custome factory: " + thread.getName());
            return thread;
        });
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

    public static void main(String[] args) {
//        func();
        funcCustomFactory();
    }
}
