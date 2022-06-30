package com.janwarlen.learn.collection.map;

import com.janwarlen.learn.Entity.Person;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapDemo {

    public static void func() {
        ConcurrentSkipListMap<Person, String> map = new ConcurrentSkipListMap();
        Person p1 = new Person("p1", 1);
        Person p2 = new Person("p2", 2);
        Person p3 = new Person("p3", 3);
        Person p4 = new Person("p4", 4);
        Person p5 = new Person("p5", 5);
        map.put(p1, "1");
        map.put(p2, "2");
        map.put(p3, "3");
        map.put(p4, "4");
        map.put(p5, "5");
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                System.out.println("before map size is " + map.size());
                Map.Entry<Person, String> entry = map.pollFirstEntry();
                Person key = entry.getKey();
                System.out.println("polled person is " + key.getName() + " age is" + key.getAge());
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        func();
    }

}
