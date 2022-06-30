package com.janwarlen.learn.thread.pool;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

    public static void funcWithLBQ() {
        // 创建一个线程池，包含5个核心线程，使用 LinkedBlockingQueue 时
        // 超过核心线程数的任务将会优先放入等待队列中，当等待队列无法插入时(无参LinkedBlockingQueue的队列长度是 Integer.MAX_VALUE )
        // 将会尝试创建新线程执行，当线程池中所有运行线程达到最大线程数，并且等待队列已满
        // 新的任务将会直接拒绝，并且抛出异常 java.util.concurrent.RejectedExecutionException
        // 当需要执行的任务数小于最大线程数时，核心线程数与最大线程数之间的空闲线程将在 keepAliveTime 内消亡
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
        for (int i = 0; i < 12; i++) {
            threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
        }
        while (!threadPoolExecutor.isShutdown()) {
            System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                    + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                    + "; pool size: " + threadPoolExecutor.getPoolSize());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void funcWithSynchronousQueue() {
        // 创建一个线程池，包含5个核心线程，使用 SynchronousQueue 时
        // 超过核心线程数的任务将会直接创建新线程执行
        // 超过最大线程数的任务将会直接拒绝，并抛出异常
        // 当需要执行的任务数小于最大线程数时，核心线程数与最大线程数之间的空闲线程将在 keepAliveTime 内消亡
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                5, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
        }
        while (!threadPoolExecutor.isShutdown()) {
            System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                    + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                    + "; pool size: " + threadPoolExecutor.getPoolSize());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void funcShutDown() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
        for (int i = 0; i < 12; i++) {
            threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
        }
        // 执行 shutdown 之后，正在执行的任务和等待队列中的任务将会继续执行
        System.out.println(System.currentTimeMillis() + " shut down.");
        // 只中断空闲线程  interruptIdleWorkers
        threadPoolExecutor.shutdown();
        System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                + "; pool size: " + threadPoolExecutor.getPoolSize());
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                + "; pool size: " + threadPoolExecutor.getPoolSize());
        // shutdown 之后拒绝新任务提交，会抛出异常
//        threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
    }

    public static void funcShutDownNow() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
        for (int i = 0; i < 12; i++) {
            threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
        }
        // 执行 shutdownNow 之后，正在执行的任务将会继续执行，未执行的任务将不会执行，并从队列中删除
        // 需要注意的是 shutdownNow 会影响正在运行的线程，内部会针对所有worker调用 interrupt ，线程状态也会切换到中断
        // 因此，如果任务有涉及中断的将会受到影响，如 sleep/wait 等等
        System.out.println(System.currentTimeMillis() + " shut down now.");
        List<Runnable> runnables = threadPoolExecutor.shutdownNow();
        System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                + "; pool size: " + threadPoolExecutor.getPoolSize());
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                + " ;active thread count: " + threadPoolExecutor.getActiveCount()
                + "; pool size: " + threadPoolExecutor.getPoolSize());
        // shutdown 之后拒绝新任务提交，会抛出异常
//        threadPoolExecutor.execute(getRunnable(threadPoolExecutor));
        for (Runnable runnable : runnables) {
            // 未执行的任务
            runnable.run();
        }
    }

    public static Runnable getRunnable(ThreadPoolExecutor threadPoolExecutor) {
        return () -> {
            try {
                Thread.sleep(1_000);
                System.out.println(System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                        + "; active thread num: " + threadPoolExecutor.getActiveCount()
                        + "; pool size: " + threadPoolExecutor.getPoolSize());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void main(String[] args) {
//        funcWithLBQ();
//        funcWithSynchronousQueue();
//        funcShutDown();
        funcShutDownNow();

    }
}
