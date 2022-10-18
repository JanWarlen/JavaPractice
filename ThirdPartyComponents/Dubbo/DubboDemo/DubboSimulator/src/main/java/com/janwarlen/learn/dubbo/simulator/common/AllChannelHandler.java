package com.janwarlen.learn.dubbo.simulator.common;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AllChannelHandler {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,
            1, TimeUnit.MINUTES,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void channelRead(Runnable r) {
        executor.execute(r);
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
