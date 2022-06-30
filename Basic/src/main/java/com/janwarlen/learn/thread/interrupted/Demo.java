package com.janwarlen.learn.thread.interrupted;

import java.util.ArrayList;

public class Demo {
    public static ArrayList<String> list1 = new ArrayList<>();
    public static ArrayList<String> list2 = new ArrayList<>();

    public static boolean interrupt = false;

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        boolean list1Interrupted = false;
        boolean list2Interrupted = false;
        while (myThread.isAlive()) {
            if (list1.size() > 500 && !list1Interrupted) {
                myThread.interrupt();
                list1Interrupted = true;
            }
            if (list2.size() > 500 && !list2Interrupted) {
                myThread.interrupt();
                list2Interrupted = true;
            }
            Thread.sleep(50);
        }
    }

}
