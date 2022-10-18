package com.janwarlen.learn.provider;

import com.janwarlen.learn.api.GreetingService;
import com.janwarlen.learn.api.POJO;
import com.janwarlen.learn.api.Result;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;

import java.io.IOException;

public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello," + name + "," + RpcContext.getContext().getAttachment("company");
    }

    @Override
    public Result<String> testGeneric(POJO pojo) {
        Result<String> result = new Result<>();
        result.setSucess(true);
        try {
            result.setData(JSON.json(pojo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void onconnect(String param) {
        System.out.println("onconnect:" + param);
    }
}
