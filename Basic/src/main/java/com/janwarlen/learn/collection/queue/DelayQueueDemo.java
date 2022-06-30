package com.janwarlen.learn.collection.queue;

import java.util.concurrent.DelayQueue;

public class DelayQueueDemo {

    public static void func() {
        CustomDelayed d1 = new CustomDelayed("1", 1);
        CustomDelayed d2 = new CustomDelayed("2", 2);
        CustomDelayed d3 = new CustomDelayed("3", 3);
        CustomDelayed d4 = new CustomDelayed("4", 4);
        CustomDelayed d5 = new CustomDelayed("5", 5);

        DelayQueue<CustomDelayed> delayQueue = new DelayQueue<>();
        delayQueue.add(d5);
        delayQueue.add(d3);
        delayQueue.add(d2);
        delayQueue.add(d4);
        delayQueue.add(d1);

        try {
            System.out.println("queue take: " + delayQueue.take().getName() + " :" + System.currentTimeMillis());
            System.out.println("queue take: " + delayQueue.take().getName() + " :" + System.currentTimeMillis());
            System.out.println("queue take: " + delayQueue.take().getName() + " :" + System.currentTimeMillis());
            System.out.println("queue take: " + delayQueue.take().getName() + " :" + System.currentTimeMillis());
            System.out.println("queue take: " + delayQueue.take().getName() + " :" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        func();
    }
}
