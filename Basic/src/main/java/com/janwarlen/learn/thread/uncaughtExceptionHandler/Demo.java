package com.janwarlen.learn.thread.uncaughtExceptionHandler;

public class Demo {

    public static void func() {
        Thread thread = new Thread(() -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("exception thread name is " + t.getName());
            e.printStackTrace();
        });
        thread.start();
    }

    public static void funcBatchSetDefaultHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("default exception handler");
            System.out.println("exception thread name is " + t.getName());
            e.printStackTrace();
        });
        Thread thread = new Thread(() -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread.start();
    }

    public static void funcGroupUncaughtExHandler() {
        ThreadGroup threadGroup = new CustomThreadGroup("demo");
        Thread thread = new Thread(threadGroup, () -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread.start();
        new Thread(threadGroup, () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("live thread report.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("thread interrupted");
                }
            }
        }).start();
    }

    /**
     * 线程未单独设置handler的会先执行通过 Thread.setDefaultUncaughtExceptionHandler 设置的默认的 exception handler
     * 然后再执行线程组的handler
     *
     * 单独设置handler的仅会执行设置的handler，默认的和线程组的不会执行
     */
    public static void funcWhichHandlerRun() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("default exception handler " + t.getName());
            e.printStackTrace();
        });
        Thread thread = new Thread(() -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("thread set handler " + t.getName());
            e.printStackTrace();
        });
        thread.start();

        ThreadGroup threadGroup = new CustomThreadGroup("demo");
        Thread thread2 = new Thread(threadGroup, () -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread2.start();
        Thread thread3 = new Thread(threadGroup, () -> {
            String name = null;
            System.out.println("name=" + name.hashCode());
            System.out.println("after ex");
        });
        thread3.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("thread set handler " + t.getName());
            e.printStackTrace();
        });
        thread3.start();
    }

    public static void main(String[] args) {
//        func();
//        funcBatchSetDefaultHandler();
//        funcGroupUncaughtExHandler();
        funcWhichHandlerRun();
    }
}
