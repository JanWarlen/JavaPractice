package com.janwarlen.learn.collection.queue;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueDemo {

    public static void func() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        new Thread(() -> {
            try {
                System.out.println("start: " + Thread.currentThread().getName());
                System.out.println("queue take: " + queue.take());
                System.out.println("end: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "consumer").start();
        new Thread(() -> {
            System.out.println("start: " + Thread.currentThread().getName());
            queue.put("value");
            System.out.println("end: " + Thread.currentThread().getName());
        }, "producer").start();
    }

    public static void funcTryTransferWithoutTake() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        new Thread(() -> {
            System.out.println("start: " + Thread.currentThread().getName());
            System.out.println("transfer res: " + queue.tryTransfer("value"));
            System.out.println("end: " + Thread.currentThread().getName());
        }, "producer").start();

    }


    public static void main(String[] args) {
//        func();
        funcTryTransferWithoutTake();
    }
}
