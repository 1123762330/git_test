package com.xn.service_consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider")
public interface TestInterface {
    @RequestMapping(value = "/test")
    String test();
}
