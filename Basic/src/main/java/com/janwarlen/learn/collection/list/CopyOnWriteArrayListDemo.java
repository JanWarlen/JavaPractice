package com.janwarlen.learn.collection.list;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        test(list);

        /**
         * subList 返回的是 COWSubList
         * 该类对查询也做了锁，如果出现并发导致数据不一致问题会抛出异常  java.util.ConcurrentModificationException
         */
        List<String> subList = list.subList(0, list.size());
        test(subList);

    }

    private static void test(List<String> list) {
        System.out.println(list.size());
        CountDownLatch countDownLatch = new CountDownLatch(10);
        CountDownLatch remove = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 存在多线程操作风险，可能会读出不同数据
                System.out.println(list.get(3));
                list.remove("1");

                remove.countDown();
            });
            thread.start();
        }
        try {
            remove.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.size());
    }
}
