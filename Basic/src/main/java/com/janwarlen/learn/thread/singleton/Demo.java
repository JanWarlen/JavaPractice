package com.janwarlen.learn.thread.singleton;

import java.util.concurrent.CountDownLatch;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            CountDownLatch latch = new CountDownLatch(1);
            CountDownLatch end = new CountDownLatch(100);
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    try {
                        latch.await();
                        SingletonDemo instance = SingletonDemo.getInstance();
                        if (0 == instance.flag) {
                            System.out.println("instance.flag == 0, system.exit");
                            System.exit(0);
                        }
                        end.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            latch.countDown();
            end.await();
//            SingletonDemo.reset();
        }
    }

}
