package com.janwarlen.learn.consumer;

import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;

public class ApiGenericConsumerForTrue {
    public static void main(String[] args) {
        ReferenceConfig<GenericService> config = new ReferenceConfig<>();
        config.setApplication(new ApplicationConfig("generic-consumer"));
//        config.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        config.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        config.setVersion("1.0.0");
        config.setGroup("dubbo");
        config.setTimeout(5_000);

        config.setInterface("com.janwarlen.learn.api.GreetingService");
        config.setGeneric(true);
        GenericService genericService = config.get();
        RpcContext.getContext().setAttachment("company", "generic");
        Object sayHello = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"world"});
        try {
            System.out.println("sayHello:" + JSON.json(sayHello));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("class", "com.janwarlen.learn.api.POJO");
        map.put("id", "1990");
        map.put("name", "test");
        Object testGeneric = genericService.$invoke("testGeneric", new String[]{"com.janwarlen.learn.api.POJO"}, new Object[]{map});
        System.out.println("testGeneric=" + testGeneric);


    }
}
