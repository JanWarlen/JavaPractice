package com.janwarlen.learn.spock.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.janwarlen.learn.spock.demo.mapper")
@SpringBootApplication
public class SpockBasicDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpockBasicDemoApplication.class, args);
    }
}
