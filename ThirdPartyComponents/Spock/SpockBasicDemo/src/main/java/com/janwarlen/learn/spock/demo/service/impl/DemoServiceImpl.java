package com.janwarlen.learn.spock.demo.service.impl;

import com.janwarlen.learn.spock.demo.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String demo(String name) {
        return "hello," + name;
    }
}
