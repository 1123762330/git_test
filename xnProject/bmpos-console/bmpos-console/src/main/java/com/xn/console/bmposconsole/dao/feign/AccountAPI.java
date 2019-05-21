package com.xn.console.bmposconsole.dao.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "account-api")
public interface AccountAPI {

    @RequestMapping(value = "/api/findSaleAddress", method = RequestMethod.GET)
    JSONObject findSaleAddress ();

    @RequestMapping(value = "/api/updateWalletRank", method = RequestMethod.GET)
    JSONObject updateWalletRank(@RequestParam("rank") Integer rank,@RequestParam("walletId") Integer walletId);

    @RequestMapping(value = "/api/selectWallet", method = RequestMethod.GET)
    JSONObject selectWalletByOne(@RequestParam("walletId") Integer walletId);
}
