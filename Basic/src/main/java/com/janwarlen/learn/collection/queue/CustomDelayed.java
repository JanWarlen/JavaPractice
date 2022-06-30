package com.janwarlen.learn.collection.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class CustomDelayed implements Delayed {

    private String name;

    private long nanoTime;

    public String getName() {
        return name;
    }

    public long getNanoTime() {
        return nanoTime;
    }

    public CustomDelayed(String name, long second) {
        this.name = name;
        this.nanoTime = System.nanoTime() + TimeUnit.SECONDS.toNanos(second);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return nanoTime - System.nanoTime();
    }

    /**
     * 内部会根据生序排列，优先执行最小的
     * 即便修改比较的顺序，执行也会根据 getDelay 的返回结果延迟执行，如果将延迟最高的排在队列队头
     * 则后续的数据获取将不会有等待效果
     * @param o the object to be compared.
     * @return 大小
     */
    @Override
    public int compareTo(Delayed o) {
        if (o instanceof CustomDelayed tmp && this.nanoTime > tmp.getNanoTime()) {
            return 1;
        } else {
            return -1;
        }
    }
}
