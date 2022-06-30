package com.janwarlen.learn.thread.locksupport;

import java.util.concurrent.locks.LockSupport;

public class Demo {

    public static void func() {
        Thread thread = new Thread(() -> {
            System.out.println("begin:" + System.currentTimeMillis());
            LockSupport.park();
            System.out.println("end:" + System.currentTimeMillis());
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LockSupport.unpark(thread);
    }

    public static void main(String[] args) {
        func();
    }

}
