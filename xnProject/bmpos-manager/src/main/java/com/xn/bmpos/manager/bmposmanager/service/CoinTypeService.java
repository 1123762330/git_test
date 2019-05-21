package com.xn.bmpos.manager.bmposmanager.service;

import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinType;
import com.xn.bmpos.manager.bmposmanager.domain.mapper.CoinTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:       CoinService
 * Author:         Administrator
 * Date:           2019/5/16 12:22
 * Description:
 */

@Service
public class CoinTypeService {

    @Autowired
    private CoinTypeMapper coinTypeMapper;

    //查询所有币种
    public HashMap<String, String> findAllCoin(){
        HashMap<String, String> stringCoinTypeHashMap = new HashMap<>();
        List<CoinType> allCoin = coinTypeMapper.findAllCoin();
        for(CoinType coinType : allCoin){
            stringCoinTypeHashMap.put(coinType.getCoinName().toLowerCase(),coinType.getPrice());
        }
        return stringCoinTypeHashMap;
    }

    //插入币种价格
    public int updateCoinPrice(String coinName,String price){
        return coinTypeMapper.updateCoinPrice(coinName,price);
    }

}
