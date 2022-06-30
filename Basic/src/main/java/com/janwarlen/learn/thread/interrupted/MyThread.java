package com.janwarlen.learn.thread.interrupted;

public class MyThread extends Thread {

    @Override
    public void run() {
        try {
            while (true) {
                if (Demo.interrupt) {
                    if (this.isInterrupted()) {
                        throw new InterruptedException("线程意外中断！");
                    }
                } else {
                    if (interrupted()) {
                        throw new InterruptedException("线程意外中断！");
                    }
                }
                for (int i = 0; i < 10_000; i++) {
                    new String("" + Math.random());
                }
                Demo.list1.add("生产数据A");
                System.out.println("list1 size=" + Demo.list1.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                if (Demo.interrupt) {
                    if (this.isInterrupted()) {
                        throw new InterruptedException("线程意外中断！");
                    }
                } else {
                    if (interrupted()) {
                        throw new InterruptedException("线程意外中断！");
                    }
                }
                for (int i = 0; i < 10_000; i++) {
                    new String("" + Math.random());
                }
                Demo.list2.add("生产数据B");
                System.out.println("list2 size=" + Demo.list2.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
