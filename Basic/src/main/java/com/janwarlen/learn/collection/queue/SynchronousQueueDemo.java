package com.janwarlen.learn.collection.queue;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

    /**
     * 一对一，该类型无法存储数据
     * take与put成对出现才能正常运行，任何单独出现都会阻塞
     */
    public static void func() {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>(true);
        /*try {
            synchronousQueue.put("1");
            System.out.println("put 1");
            synchronousQueue.put("2");
            System.out.println("put 2");
            synchronousQueue.put("3");
            System.out.println("put 3");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        // sout 无法体现调用顺序建议使用debug进行断点观察
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("start take");
                    System.out.println("take: " + synchronousQueue.take());
                    System.out.println("end take");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("start put: " + i);
                    synchronousQueue.put(String.valueOf(i));
                    System.out.println("put: " + i);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(synchronousQueue.size());
    }

    public static void main(String[] args) {
        func();
    }
}
