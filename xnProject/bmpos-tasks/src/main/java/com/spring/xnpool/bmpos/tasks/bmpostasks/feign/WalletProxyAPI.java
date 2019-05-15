package com.spring.xnpool.bmpos.tasks.bmpostasks.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Component
@FeignClient(name = "wallet-proxy")
public interface WalletProxyAPI {
    @RequestMapping(value = "/api/can_connect", method = RequestMethod.GET)
    JSONObject can_connect(@RequestParam("coin") String coin);

    @RequestMapping(value = "/api/help", method = RequestMethod.GET)
    JSONObject help(@RequestParam("coin") String coin);

    @RequestMapping(value = "/api/getrealbalance", method = RequestMethod.GET)
    JSONObject getrealbalance(@RequestParam("coin") String coin);

    @RequestMapping(value = "/api/validateAddress", method = RequestMethod.GET)
    JSONObject validateAddress(@RequestParam("coin") String coin,@RequestParam("coinAddress") String coinAddress);

    @RequestMapping(value = "/api/sendToAddress", method = RequestMethod.GET)
    JSONObject sendToAddress(@RequestParam("coin") String coin,@RequestParam("coinAddress") String coinAddress,@RequestParam("amount") BigDecimal amount);

    @RequestMapping(value = "/api/getTransaction", method = RequestMethod.GET)
    JSONObject getTransaction(@RequestParam("coin") String coin,@RequestParam("rpcTxid") String rpcTxid);

    @RequestMapping(value = "/api/getDifficulty", method = RequestMethod.GET)
    JSONObject getDifficulty(@RequestParam("coin") String coin);

    @RequestMapping(value = "/api/getpool", method = RequestMethod.GET)
    JSONObject getpool(@RequestParam("coin") String coin);
}

