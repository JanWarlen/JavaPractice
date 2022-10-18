package com.janwarlen.learn.dubbo.simulator;

import com.janwarlen.learn.dubbo.simulator.client.RpcClient;

import java.util.concurrent.CompletableFuture;

public class Demo {

    private static final RpcClient RPC_CLIENT = new RpcClient();

    public static void main(String[] args) {
        System.out.println("client:" + RPC_CLIENT.rpcSyncCall("hello"));
        System.out.println("-------------------------------");
        CompletableFuture<String> future = RPC_CLIENT.rpcAsyncCall("async hello");
        future.whenComplete((v, t) -> {
            if (null != t) {
                t.printStackTrace();
            } else {
                System.out.println("client:" + v);
            }
        });
    }
}
