package com.janwarlen.learn.consumer;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

/**
 * 服务降级+熔断处理
 */
public class APiConsumerMockResult {

	public static void mockResult(String type) {
		// (1)获取服务注册中心工厂
		RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class)
				.getAdaptiveExtension();
		// (2)根据zk地址，获取具体的zk注册中心的客户端实例
		Registry registry2 = registryFactory.getRegistry(URL.valueOf("zookeeper://192.168.125.201:2181"));

		// directory.subscribe(subscribeUrl.addParameter(CATEGORY_KEY,
		// PROVIDERS_CATEGORY + "," + CONFIGURATORS_CATEGORY + "," + ROUTERS_CATEGORY));

		// (3)注册降级方案到zk
		// override://0.0.0.0 表示针对所有服务
		registry2.register(URL.valueOf(
				"override://0.0.0.0/com.books.dubbo.demo.api.GreetingService?category=configurators&dynamic=false&application=first-dubbo-consumer&"
						+ "mock=" + type + ":return+null&group=dubbo&version=1.0.0"));

		//(4)取消配置
//		registry2.unregister(URL.valueOf(
//				"override://0.0.0.0/com.books.dubbo.demo.api.GreetingService?category=configurators&dynamic=false&application=first-dubbo-consumer&"
//						+ "mock=" + type + ":return+null&group=dubbo&version=1.0.0"));
	}

	public static void main(String[] args) throws InterruptedException {

		// mock=force:result+null;
		// 不发起远程调用，直接返回
		mockResult("force");

		// mock=fail:result+null;
		// mockResult("fail");

	}
}