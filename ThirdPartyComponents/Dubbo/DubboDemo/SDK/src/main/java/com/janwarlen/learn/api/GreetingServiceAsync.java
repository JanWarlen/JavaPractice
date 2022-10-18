package com.janwarlen.learn.api;

import java.util.concurrent.CompletableFuture;

public interface GreetingServiceAsync {
    CompletableFuture<String> sayHello(String name);
}
