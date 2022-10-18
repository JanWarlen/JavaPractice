package com.janwarlen.learn.dubbo.simulator.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FutureMapUtil {

    private static final ConcurrentHashMap<String, CompletableFuture> futureMap = new ConcurrentHashMap<>();

    public static void put(String id, CompletableFuture future) {
        futureMap.put(id, future);
    }

    public static CompletableFuture remove(String id) {
        return futureMap.remove(id);
    }
}
