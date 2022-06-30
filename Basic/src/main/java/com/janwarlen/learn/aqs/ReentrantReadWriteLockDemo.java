package com.janwarlen.learn.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    /**
     * 仅读-读共享
     */
    public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static Runnable read = () -> {
        lock.readLock().lock();
        System.out.println("get read lock:" + Thread.currentThread().getName() + " at:" + System.currentTimeMillis());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.readLock().unlock();
    };

    private static Runnable write = () -> {
        lock.writeLock().lock();
        System.out.println("get write lock:" + Thread.currentThread().getName() + " at:" + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.writeLock().unlock();
    };


    public static void funcReadRead() {
        List<Thread> list = new ArrayList<>();
        list.add(new Thread(read));
        list.add(new Thread(read));
        list.add(new Thread(read));
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void funcWriteWrite() {
        List<Thread> list = new ArrayList<>();
        list.add(new Thread(write));
        list.add(new Thread(write));
        list.add(new Thread(write));
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void funcReadWrite() {
        List<Thread> list = new ArrayList<>();
        list.add(new Thread(read));
        list.add(new Thread(write));
        list.add(new Thread(write));
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void funcWriteRead() {
        List<Thread> list = new ArrayList<>();
        list.add(new Thread(write));
        list.add(new Thread(read));
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
//        funcReadRead();
//        funcWriteWrite();
//        funcReadWrite();
        funcWriteRead();
    }

}
