package com.xn.service_consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestInterface testInterface;

    @GetMapping("/test")
    public String test(){
        return testInterface.test();
    }
}
