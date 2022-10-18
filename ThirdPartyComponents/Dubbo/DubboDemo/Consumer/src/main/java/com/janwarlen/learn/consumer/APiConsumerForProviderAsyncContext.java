package com.janwarlen.learn.consumer;

import com.janwarlen.learn.api.GreetingServiceRpcContext;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

public class APiConsumerForProviderAsyncContext {
    public static void main(String[] args) {
        ReferenceConfig<GreetingServiceRpcContext> referenceConfig = new ReferenceConfig<GreetingServiceRpcContext>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        referenceConfig.setInterface(GreetingServiceRpcContext.class);
        referenceConfig.setTimeout(5000);

        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        GreetingServiceRpcContext greetingService = referenceConfig.get();

        //设置隐士参数
        RpcContext.getContext().setAttachment("company", "AsyncContext");
        String result = greetingService.sayHello("world");
        System.out.println(result);
    }
}
