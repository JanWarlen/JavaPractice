package com.janwarlen.learn.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static ReentrantLock fairLock = new ReentrantLock(true);
    public static ReentrantLock unfairLock = new ReentrantLock();

    public static CountDownLatch countDownLatch = new CountDownLatch(10);
    public static CountDownLatch func = new CountDownLatch(10);

    private static final List<String> messages = new ArrayList<>(20);

    public static String isFair(ReentrantLock lock) {
        return lock.isFair() ? "fair" : "unfair";
    }

    public static void funcLock(ReentrantLock lock) {
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long before = System.currentTimeMillis();
        messages.add(Thread.currentThread().getName() + ":before " + isFair(lock) + " lock:currentTimeMillis:" + before);
        lock.lock();
        messages.add(Thread.currentThread().getName() + "getQueueLength:" + lock.getQueueLength());
        long after = System.currentTimeMillis();
        messages.add(Thread.currentThread().getName() + ":after " + isFair(lock) + " lock:currentTimeMillis:" + after + ":cost:" + (after - before));
        lock.unlock();
        func.countDown();
    }

    /**
     * 公平锁和非公平锁实现一样，没区别
     *
     * @param lock 锁
     */
    public static void funcTryLock(ReentrantLock lock) {
        System.out.println(Thread.currentThread().getName() + ":before " + isFair(lock) + " try lock:currentTimeMillis:" + System.currentTimeMillis());
        if (lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ":get lock by tryLock:" + isFair(lock));
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + ":after " + isFair(lock) + " try lock:currentTimeMillis:" + System.currentTimeMillis());
        func.countDown();
    }

    public static void funcTryLockWithTimeLimit(ReentrantLock lock) {
        System.out.println(Thread.currentThread().getName() + ":before " + isFair(lock) + " try lock:currentTimeMillis:" + System.currentTimeMillis());
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + ":get lock by tryLock with time limit:currentTimeMillis:" + System.currentTimeMillis());
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ":after " + isFair(lock) + " try lock:currentTimeMillis:" + System.currentTimeMillis());
        func.countDown();
    }

    /**
     * lockInterruptibly 是在使用该方法进入等待后，线程可被中断（获取锁后，不在等待队列的线程会恢复可中断属性）
     * 其他方法进入获取锁的等待队列是无法中断的
     * @param lock 锁
     */
    public static void funcInterruptLock(ReentrantLock lock) {
        System.out.println(Thread.currentThread().getName() + ":before " + isFair(lock) + " interrupt lock:currentTimeMillis:" + System.currentTimeMillis());
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + ":after lockInterruptibly");
            Thread.sleep(1000);
            lock.unlock();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":InterruptedException");
        }
        func.countDown();
    }

    /**
     * ReentrantLock 有可重入锁的可能，因此lock() 与 unlock() 必须成对出现
     * 可以理解为每重入锁一次，就是再加一把锁，而unlock()每次调用只能解开一把锁，如果调用次数不够，还会剩下锁限制
     * 因此其他线程就依旧无法获得锁
     * @param lock 锁
     */
    public static void funcHold(ReentrantLock lock) {
        System.out.println("funcHold:before lock");
        lock.lock();
        System.out.println("funcHold:get lock");
        funcHoldSub(lock);
        lock.unlock();
    }

    public static void funcHoldSub(ReentrantLock lock) {
        System.out.println("funcHoldSub:before lock");
        lock.lock();
        System.out.println("funcHoldSub:after lock");
    }

    /**
     * condition.awaitUninterruptibly() 会刷新线程是否可中断的限制，即便获取锁是通过
     * @param lock
     * @param condition
     */
    public static void funcInterrupt(ReentrantLock lock, Condition condition) {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + ":after lockInterruptibly");
            condition.awaitUninterruptibly();
            lock.unlock();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":InterruptedException");
        }
        func.countDown();
    }


    public static void main(String[] args) throws InterruptedException {
        /*func = new CountDownLatch(10);
        lockDemo(fairLock, 10);
        func.await();
        func = new CountDownLatch(10);
        lockDemo(unfairLock, 10);
        func.await();
        for (String message : messages) {
            System.out.println(message);
        }
        */
/*        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcTryLock(fairLock), false);
        func.await();
        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcTryLock(unfairLock), false);
        func.await();*/

/*
        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcTryLockWithTimeLimit(fairLock), false);
        func.await();
        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcTryLockWithTimeLimit(unfairLock), false);
        func.await();
*/

        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcInterruptLock(fairLock), true);
        func.await();
        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcInterruptLock(unfairLock), true);
        func.await();

        /*func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcInterrupt(fairLock, fairLock.newCondition()), true);
        func.await();
        func = new CountDownLatch(5);
        testDemo(5, () -> ReentrantLockDemo.funcInterrupt(unfairLock, unfairLock.newCondition()), true);
        func.await();*/

//        testDemo(5, () -> ReentrantLockDemo.funcHold(fairLock), false);
    }

    /**
     * 只能通过 lock.getQueueLength() 去观察公平锁和非公平锁的区别
     * 公平锁是排队数多，然后通过一个一个获取降低排队数
     * 非公平锁因为在首次锁获取时无论是否有排队等待都会尝试获取一次锁，并且锁竞争的消耗小。因此排队等待的数量一直较少
     * <p>
     * thread_9:before unfair lock:currentTimeMillis:1656338672670
     * thread_9getQueueLength:0
     * thread_0:before unfair lock:currentTimeMillis:1656338672671
     * thread_9:after unfair lock:currentTimeMillis:1656338672671:cost:1
     * thread_0getQueueLength:0
     * thread_0:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_1:before unfair lock:currentTimeMillis:1656338672671
     * thread_1getQueueLength:0
     * thread_1:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_2:before unfair lock:currentTimeMillis:1656338672671
     * thread_3:before unfair lock:currentTimeMillis:1656338672671
     * thread_2getQueueLength:0
     * thread_2:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_3getQueueLength:0
     * thread_4:before unfair lock:currentTimeMillis:1656338672671
     * thread_5:before unfair lock:currentTimeMillis:1656338672671
     * thread_5getQueueLength:1
     * thread_5:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_4getQueueLength:0
     * thread_6:before unfair lock:currentTimeMillis:1656338672671
     * thread_4:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_7:before unfair lock:currentTimeMillis:1656338672671
     * thread_7getQueueLength:1
     * thread_7:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_8:before unfair lock:currentTimeMillis:1656338672671
     * thread_8getQueueLength:1
     * thread_8:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * thread_6getQueueLength:0
     * thread_6:after unfair lock:currentTimeMillis:1656338672671:cost:0
     * <p>
     * 可以通过 thread_6 看到非公平锁的体现，即 thread_6 在排队的序列中，但是其他的线程可以先竞争，而不是直接去排队
     */
    private static void lockDemo(ReentrantLock lock, int num) throws InterruptedException {
        List<Thread> thds = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            thds.add(new Thread(() -> ReentrantLockDemo.funcLock(lock), "thread_" + i));
        }
        countDownLatch = new CountDownLatch(thds.size());
        for (Thread thd : thds) {
            thd.start();
        }
    }

    private static void testDemo(int num, Runnable runnable, boolean interrupt) throws InterruptedException {
        List<Thread> thds = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            thds.add(new Thread(runnable, "thread_" + i));
        }
        for (Thread thd : thds) {
            thd.start();
        }
        if (interrupt) {
            for (Thread thd : thds) {
                thd.interrupt();
            }
        }
    }
}
