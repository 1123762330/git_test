package com.xn.bmpos.manager.bmposmanager.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class RunneConfig implements CommandLineRunner{
    @Autowired
    private PriceTask priceTask;

    @Override
    public void run(String... args) throws Exception {
        priceTask.insertCoinPrice();

    }

}
