package com.janwarlen.learn.threadpool;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

    static InheritableThreadLocal<String> itl = new InheritableThreadLocal();
    static TransmittableThreadLocal<String> ttl = new TransmittableThreadLocal<>();
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    static ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);

    /**
     * pool-1-thread-1 get itl:first set
     * pool-1-thread-2 get ttl:first set
     * pool-1-thread-2 get itl:first set
     * pool-1-thread-1 get ttl:second set
     * pool-1-thread-2 get itl:first set
     * pool-1-thread-1 get ttl:third set
     */
    public static void main(String[] args) {
        itl.set("first set");
        ttl.set("first set");
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get itl:" + itl.get());
        });
        ttlExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get ttl:" + ttl.get());
        });
        itl.set("second set");
        ttl.set("second set");
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get itl:" + itl.get());
        });
        ttlExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get ttl:" + ttl.get());
        });
        itl.set("third set");
        ttl.set("third set");
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get itl:" + itl.get());
        });
        ttlExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " get ttl:" + ttl.get());
        });
    }
}
