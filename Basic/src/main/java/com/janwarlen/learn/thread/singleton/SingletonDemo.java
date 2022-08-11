package com.janwarlen.learn.thread.singleton;

import java.util.Random;

public class SingletonDemo {

    private SingletonDemo() {
    }

    public int flag = 0;

    /*
    // 准备测试 volatile ，但并没有出现期待的现象
private static SingletonDemo instance = null;
public SingletonDemo() {
    flag = new Random().nextInt(200) + 1;
}

public static SingletonDemo getInstance() {
    if (null == instance) {
        synchronized (SingletonDemo.class) {
            if (null == instance) {
                instance = new SingletonDemo();
            }
        }
    }
    return instance;
}

public static void reset() {
    instance = null;
}
*/
    private static class SingletonDemoHandler {
        private static SingletonDemo instance = new SingletonDemo();
    }

    public static SingletonDemo getInstance() {
        return SingletonDemoHandler.instance;
    }

}
