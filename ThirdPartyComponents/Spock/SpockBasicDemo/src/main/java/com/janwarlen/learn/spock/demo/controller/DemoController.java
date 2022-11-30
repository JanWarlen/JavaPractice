package com.janwarlen.learn.spock.demo.controller;

import com.janwarlen.learn.spock.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo")
    public String demo(@RequestParam String name) {
        return demoService.demo(name);
    }
}
