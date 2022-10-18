package com.janwarlen.learn.api;

import com.janwarlen.learn.api.GreetingService;
import com.janwarlen.learn.api.POJO;
import com.janwarlen.learn.api.Result;

/**
 * 命名规则固定 接口名+Mock
 */
public class GreetingServiceMock implements GreetingService {
    @Override
    public String sayHello(String name) {
        return "mock value";
    }

    @Override
    public Result<String> testGeneric(POJO pojo) {
        return null;
    }

    @Override
    public void onconnect(String param) {
    }
}
