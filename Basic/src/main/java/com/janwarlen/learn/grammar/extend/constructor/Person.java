package com.janwarlen.learn.grammar.extend.constructor;

public class Person {

    private String name;

    private int age;

    private int gender;

    private int tall;

    private int weight;

    public Person(String name, int age, int gender, int tall, int weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.tall = tall;
        this.weight = weight;
    }

    public Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.gender = builder.gender;
        this.tall = builder.tall;
        this.weight = builder.weight;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;

        private int age;

        private int gender;

        private int tall;

        private int weight;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setGender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder setTall(int tall) {
            this.tall = tall;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public static void main(String[] args) {
        Person test1 = new Person("test", 18, 1, 180, 130);
        Person test2 = Person.newBuilder()
                .setName("test")
                .setAge(18)
                .setGender(1)
                .setTall(180)
                .setWeight(130)
                .build();
//        new Test();
    }

}
