package com.janwarlen.learn.spock.demo.controller

import com.janwarlen.learn.spock.demo.SpockBasicDemoApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = SpockBasicDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTest extends Specification {


    @Autowired
    private DemoController demoController;


    def "testDemo"() {
        given:

        expect:
        demoController.demo(name) == expect
        where:
        name   | expect
        "test" | "hello,test"
    }
}
