package com.janwarlen.learn.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectPolicyDemo {

    /**
     * 默认策略
     * 拒绝时抛出异常 java.util.concurrent.RejectedExecutionException
     * 影响提交任务线程
     */
    public static void funcAbortPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1));
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(ThreadPoolExecutorDemo.getRunnable(threadPoolExecutor));
        }
    }

    /**
     * 提交任务线程处理拒绝任务策略
     * 会影响提交任务线程（阻塞）
     */
    public static void funcCallerRunsPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(ThreadPoolExecutorDemo.getRunnable(threadPoolExecutor));
        }
    }

    /**
     * 抛弃最早进入等待队列的任务，将新任务加入等待队列中
     */
    public static void funcDiscardOldestPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2),
                new ThreadPoolExecutor.DiscardOldestPolicy());
//        策略就是 RejectedExecutionHandler，如果手动设置，则会覆盖，设置的策略将不会执行
        /*List<Runnable> list = new ArrayList<>();
        threadPoolExecutor.setRejectedExecutionHandler((r, executor) -> {
            if (r instanceof CustomRunable cr) {
                System.out.println("task rejected:" + cr.getNum());
            }
            BlockingQueue<Runnable> queue = executor.getQueue();
            Runnable[] runnables = new Runnable[2];
            queue.toArray(runnables);
            for (Runnable runnable : runnables) {
                if (runnable instanceof CustomRunable cr) {
                    System.out.println("task in queue: " + cr.getNum());
                }
            }
            list.add(r);
        });*/
        for (int i = 0; i < 8; i++) {
            threadPoolExecutor.execute(new CustomRunable(i, threadPoolExecutor));
        }
    }

    /**
     * 被拒绝的任务直接抛弃，即等同于 RejectedExecutionHandler 中不做任何处理（源码也是如此）
     */
    public static void funcDiscardPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 8; i++) {
            threadPoolExecutor.execute(new CustomRunable(i, threadPoolExecutor));
        }
    }

    static class CustomRunable implements Runnable {

        private int num;

        private ThreadPoolExecutor threadPoolExecutor;

        public CustomRunable(int num, ThreadPoolExecutor threadPoolExecutor) {
            this.num = num;
            this.threadPoolExecutor = threadPoolExecutor;
        }

        public int getNum() {
            return num;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1_000);
                System.out.println("task num: " + num + " ;" + System.currentTimeMillis() + " ;running: " + Thread.currentThread().getName()
                        + "; active thread num: " + threadPoolExecutor.getActiveCount()
                        + "; pool size: " + threadPoolExecutor.getPoolSize());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
//        funcAbortPolicy();
//        funcCallerRunsPolicy();
//        funcDiscardOldestPolicy();
        funcDiscardPolicy();
    }
}
