package com.janwarlen.learn.api;

public interface GreetingService {
    String sayHello(String name);

    Result<String> testGeneric(POJO pojo);

    void onconnect(String param);
}
