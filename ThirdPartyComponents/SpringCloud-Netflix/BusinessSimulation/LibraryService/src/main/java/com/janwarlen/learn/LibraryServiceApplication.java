package com.janwarlen.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class LibraryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }
}
