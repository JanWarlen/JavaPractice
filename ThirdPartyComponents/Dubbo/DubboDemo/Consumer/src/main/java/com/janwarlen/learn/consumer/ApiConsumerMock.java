package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

public class ApiConsumerMock {
    public static void main(String[] args) {
        ReferenceConfig<GreetingService> greetingServiceReferenceConfig = new ReferenceConfig<>();
        greetingServiceReferenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        greetingServiceReferenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        greetingServiceReferenceConfig.setInterface(GreetingService.class);
        greetingServiceReferenceConfig.setTimeout(5_000);
        greetingServiceReferenceConfig.setVersion("1.0.0");
        greetingServiceReferenceConfig.setGroup("dubbo");

        greetingServiceReferenceConfig.setCheck(false);
        greetingServiceReferenceConfig.setMock(true);

        GreetingService greetingService = greetingServiceReferenceConfig.get();
        RpcContext.getContext().setAttachment("company", "mock");
        System.out.println(greetingService.sayHello("demo"));
    }
}
