package com.janwarlen.learn.provider;

import com.janwarlen.learn.api.GreetingServiceRpcContext;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreetingServiceAsyncContextImpl implements GreetingServiceRpcContext {
    private final ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(),
            new NamedThreadFactory("biz-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy());
    @Override
    public String sayHello(String name) {
        AsyncContext asyncContext = RpcContext.startAsync();
        bizThreadPool.execute(() -> {
            // 把上下文放到当前执行线程中，必须放在第一句
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            asyncContext.write("hello," + name + "," + RpcContext.getContext().getAttachment("company"));
        });
        return null;
    }
}
