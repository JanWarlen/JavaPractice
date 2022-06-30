package com.janwarlen.learn.thread.daemon;

public class Demo {

    /**
     * 一些应用监控类场景可以用到
     */
    public static void func() {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println("i=" + i++);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("用户线程即将结束，守护线程也将消亡...");
    }

    public static void main(String[] args) {
        func();
    }
}
