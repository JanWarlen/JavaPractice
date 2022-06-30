package com.janwarlen.learn.collection.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

    public static void func() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        // cas搭配synchronized使用
        map.put("1", "1");
        map.remove("1");
        map.get("1");
        // 查询会出现脏读问题
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("get:" + map.get("1"));

            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                map.put("1", map.get("1") + "1");
            }
        }).start();
    }

    public static void main(String[] args) {
        func();
    }
}
