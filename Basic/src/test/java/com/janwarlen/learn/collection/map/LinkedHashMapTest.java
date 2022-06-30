package com.janwarlen.learn.collection.map;

import org.junit.Test;

import java.util.LinkedHashMap;

public class LinkedHashMapTest {

    @Test
    public void testCreate() {
        LinkedHashMap<Integer, Integer> test = new LinkedHashMap();
        test.put(Integer.valueOf(1), Integer.valueOf(1));
    }
}
