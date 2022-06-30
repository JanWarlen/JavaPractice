package com.janwarlen.learn.thread.uncaughtExceptionHandler;

public class CustomThreadGroup extends ThreadGroup {
    public CustomThreadGroup(String name) {
        super(name);
    }

    public CustomThreadGroup(ThreadGroup parent, String name) {
        super(parent, name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        System.out.println("custom handler " + t.getName());
        this.interrupt();
    }
}
