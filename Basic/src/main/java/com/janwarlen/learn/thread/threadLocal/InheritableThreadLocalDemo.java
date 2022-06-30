package com.janwarlen.learn.thread.threadLocal;

public class InheritableThreadLocalDemo {

    public static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static InheritableThreadLocal<Object> threadLocalObj = new InheritableThreadLocal<>();

    /**
     * 自线程的继承父线程的 InheritableThreadLocal 仅是在创建线程时，将父线程的map数据导入到子线程的 map 中，并非子线程的map包含父线程的map
     * 父线程在子线程创建结束后更改 InheritableThreadLocal 值（set），子线程不会同步修改
     * 对于内容可变对象来说，仅改变对象内部值，而不是修改对象的引用，子线程因为与父线程使用的是同一个对象引用，因此是同步更新的
     */
    public static void funcSetNewString() {
        threadLocal.set("1");
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("sub thread get:" + threadLocal.get());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        System.out.println("main get:" + threadLocal.get());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadLocal.set("2");
        System.out.println("main get:" + threadLocal.get());
    }

    public static void funcSetNewObject() {
        Person person = new Person("1");
        threadLocalObj.set(person);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("sub thread get:" + threadLocalObj.get());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        System.out.println("main get:" + threadLocalObj.get());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadLocalObj.set(new Person("2"));
        System.out.println("main get:" + threadLocalObj.get());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        person.name = "new 1";
    }


    public static void main(String[] args) {
//        funcSetNewString();
        funcSetNewObject();
    }

    static class Person {
        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
