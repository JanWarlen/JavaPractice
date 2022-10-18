package com.janwarlen.learn.consumer;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * 有bug，类型转换异常
 */
public class ApiGenericConsumerForBean {
    public static void main(String[] args) {
        ReferenceConfig<GenericService> config = new ReferenceConfig<>();
        config.setApplication(new ApplicationConfig("generic-consumer"));
        config.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        config.setVersion("1.0.0");
        config.setGroup("dubbo");
        config.setTimeout(5_000);

        config.setInterface("com.janwarlen.learn.api.GreetingService");
        config.setGeneric("bean");

        GenericService genericService = config.get();

        JavaBeanDescriptor world = JavaBeanSerializeUtil.serialize("world");
        Object sayHello = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{world});
        Object deserialize = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) sayHello);
        System.out.println("deserialize=" + deserialize);
    }
}
