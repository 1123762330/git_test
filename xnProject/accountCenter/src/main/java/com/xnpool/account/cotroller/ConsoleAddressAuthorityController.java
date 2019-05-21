package com.xnpool.account.cotroller;

import com.xnpool.account.entity.SaleAddress;
import com.xnpool.account.entity.SaleAddressAll;
import com.xnpool.account.service.ISaleAddressService;
import com.xnpool.account.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author: zly
 * @Date: 2019/5/17 19:08
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ConsoleAddressAuthorityController {
    @Autowired
    ISaleAddressService saleAddressService;
    /**
     * 查询所有钱包的权限
     * @return
     */
    @RequestMapping("/findSaleAddress")
    public Resp findSaleAddress() {
        List<SaleAddressAll> SaleAddressAllList = saleAddressService.findSaleAddress();
        log.info("SaleAddressAllList的数据是:"+SaleAddressAllList);
        return Resp.success(SaleAddressAllList);
    }

    /**
     * 修改钱包级别
     * @param rank
     * @param walletId
     * @return
     */
    @RequestMapping("/updateWalletRank")
    public Resp updateWalletRank(@RequestParam("rank")Integer rank, @RequestParam("walletId")Integer walletId) {
        saleAddressService.updateWalletRank(rank,walletId);
        return Resp.success();
    }

    /**
     * 修改数据前先回显数据
     * @param walletId
     * @return
     */
    @RequestMapping("/selectWallet")
    public Resp selectWallet(@RequestParam("walletId")Integer walletId) {
        SaleAddressAll saleAddress=saleAddressService.selectWallet(walletId);
        return Resp.success(saleAddress);
    }




}
