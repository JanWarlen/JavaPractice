package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

public class ApiConsumer {
    public static void main(String[] args) {
        ReferenceConfig<GreetingService> greetingServiceReferenceConfig = new ReferenceConfig<>();
        greetingServiceReferenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
//        greetingServiceReferenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        greetingServiceReferenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        greetingServiceReferenceConfig.setInterface(GreetingService.class);
        greetingServiceReferenceConfig.setTimeout(5_000);
//        greetingServiceReferenceConfig.setLoadbalance("myLoadBalance");
//        greetingServiceReferenceConfig.setCluster("myBroadcast");
        greetingServiceReferenceConfig.setVersion("1.0.0");
        greetingServiceReferenceConfig.setGroup("dubbo");
        // 消费端获取服务时，会进行初始化（可以理解为启动）
        GreetingService greetingService = greetingServiceReferenceConfig.get();
        RpcContext.getContext().setAttachment("company", "test");

        // 阻塞直到调用返回结果
//        System.out.println(greetingService.sayHello("demo"));
        greetingService.onconnect("demo");
    }
}
