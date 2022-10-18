package com.janwarlen.learn.consumer;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.io.UnsafeByteArrayOutputStream;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;

/**
 * ClassCastException
 */
public class ApiGenericConsumerForNativeJava {
    public static void main(String[] args) {
        ReferenceConfig<GenericService> config = new ReferenceConfig<>();
        config.setApplication(new ApplicationConfig("generic-consumer"));
        config.setRegistry(new RegistryConfig("zookeeper://192.168.125.201:2181"));
        config.setVersion("1.0.0");
        config.setGroup("dubbo");
        config.setTimeout(5_000);

        config.setInterface("com.janwarlen.learn.api.GreetingService");
        config.setGeneric("nativejava");

        GenericService genericService = config.get();
        UnsafeByteArrayOutputStream out = new UnsafeByteArrayOutputStream();
        try {
            ExtensionLoader.getExtensionLoader(Serialization.class)
                    .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                    .serialize(null, out)
                    .writeObject("native");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Object sayHello = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{out.toByteArray()});
        UnsafeByteArrayInputStream in = new UnsafeByteArrayInputStream((byte[]) sayHello);
        try {
            System.out.println(ExtensionLoader.getExtensionLoader(Serialization.class)
                    .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                    .deserialize(null, in).readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
