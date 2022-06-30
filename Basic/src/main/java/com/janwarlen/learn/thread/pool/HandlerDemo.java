package com.janwarlen.learn.thread.pool;

import java.util.concurrent.*;

public class HandlerDemo {

    public static void funcUncaughtExceptionHandler() {
        ExecutorService executorService = Executors.newFixedThreadPool(5, (r) -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println("统一的异常处理中心设置");
            });
            return thread;
        });
        executorService.execute(() -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void funcRejectedExecutionHandler() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        threadPoolExecutor.setRejectedExecutionHandler((r, excutor) -> {
            System.out.println("被拒绝的任务集中处理中心");
        });
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(ThreadPoolExecutorDemo.getRunnable(threadPoolExecutor));
        }
    }

    public static void main(String[] args) {
//        funcUncaughtExceptionHandler();
        funcRejectedExecutionHandler();
    }
}
