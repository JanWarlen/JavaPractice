package com.janwarlen.learn.thread.group;

public class Demo {

    public static void func() {
        ThreadGroup demo = new ThreadGroup("demo");
        new Thread(demo, () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("this is " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(demo, () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("this is " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        System.out.println("active all thread num is " + demo.activeCount());
        System.out.println("thread group name is " + demo.getName());
    }

    public static void funcSubGroup() {
        ThreadGroup main = Thread.currentThread().getThreadGroup();
        ThreadGroup sub = new ThreadGroup(main, "sub");
        ThreadGroup grand = new ThreadGroup(sub, "grand");
        new Thread(sub, () -> {
            try {
                System.out.println("thread run");
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "subGroupThread").start();
        ThreadGroup[] threadGroups = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        // 会递归搜罗所有group
        Thread.currentThread().getThreadGroup().enumerate(threadGroups);
        for (ThreadGroup threadGroup : threadGroups) {
            System.out.println("group name is " + threadGroup.getName());
            System.out.println("active thread num is " + threadGroup.activeCount());
        }
        try {
            Thread.sleep(4_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (ThreadGroup threadGroup : threadGroups) {
            System.out.println("group name is " + threadGroup.getName());
            System.out.println("active thread num is " + threadGroup.activeCount());
        }
    }

    public static void funcInterruptGroupThreads() {
        ThreadGroup demo = new ThreadGroup("demo");
        for (int i = 0; i < 5; i++) {
            new Thread(demo, () -> {
                System.out.println(Thread.currentThread().getName() + " is ready");
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // 额外添加一次interrupt是因为 ThreadGroup 调用 interrupt 是调用线程的interrupt
                        // 而线程的 interrupt 在线程是以下几种状态时，是抛出异常并会同步将interrupt状态标记变量值恢复为正常
                        /**
                         * If this thread is blocked in an invocation of the
                         * wait(), wait(long), or wait(long, int) methods of the Object class,
                         * or of the join(), join(long), join(long, int), sleep(long),
                         * or sleep(long, int) methods of this class,
                         * then its interrupt status will be cleared and it will receive an InterruptedException.
                         */
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " is interrupted.");
                    }
                }
                System.out.println(Thread.currentThread().getName() + " will finish.");
            }).start();
        }
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        demo.interrupt();
        System.out.println("thread group demo is interrupted.");
    }

    public static void main(String[] args) {
//        func();
//        funcSubGroup();
        funcInterruptGroupThreads();
    }

}
