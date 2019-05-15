package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;



import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily.DailyVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.BlocksMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.TransacTionsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.AccountAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.RewardAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IDailyService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IErrorService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ITaskManagerService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.TransacTionService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DailyServiceImpl implements IDailyService {
    private Logger log = LoggerFactory.getLogger(DailyServiceImpl.class);
    @Autowired
    ConfigMapper settingMapper;
    @Autowired
    TransacTionService transacTionService;
    @Autowired
    BlocksMapper blocksMapper;
    @Autowired
    TransacTionsMapper transacTionsMapper;
    @Autowired
    IErrorService errorService;
    @Autowired
    RewardAPI rewardAPI;
    @Autowired
    AccountAPI accountAPI;
    public void dailyExe(String coin) {
        log.info("========开始执行日结======");
        //获取优秀auto-payouts
        Integer limit = Integer.valueOf(findValue("txlimit_auto"));
        Double confirmed = Double.valueOf(findValue("confirmed"));
         List<DailyVO> aAutoPayouts = transacTionService.getAPQueue(limit,confirmed);
        //取我们的自动支付，处理它们
        if("1".equals(findValue("disable_auto_payouts"))){
         log.error("自动支付已禁用！");
         errorService.insertToPluginsid(ITaskManagerService.DAILY_PLUGINS_ID,"自动支付已禁用！");
         return;
        }
        if(aAutoPayouts == null||aAutoPayouts.isEmpty()){
            log.error("没有支付数据！");
            errorService.insertToPluginsid(ITaskManagerService.DAILY_PLUGINS_ID,"没有支付数据！");
            return;
        }
        //String aSendMany = null;
        //先计算和
        BigDecimal dAPTotalAmount = BigDecimal.valueOf(0.0);
        for(DailyVO aUserData:aAutoPayouts){
            dAPTotalAmount = dAPTotalAmount.add(aUserData.getConfirmed());
        }
        for(DailyVO aUserData:aAutoPayouts){
            createDebitDailyRecord(aUserData.getAccountId(),aUserData.getCoinAddress(),aUserData.getConfirmed(),aUserData.getId());
            BigDecimal userDataConfirmed = aUserData.getConfirmed();
            Double balance = Double.valueOf(userDataConfirmed.toString());
            JSONObject usernameByCoinAndAddress = accountAPI.getUsernameByCoinAndAddress(aUserData.getCoinAddress(), coin);
            if(usernameByCoinAndAddress.size()>1&&usernameByCoinAndAddress.getInt("state")==200){
                String accountName = usernameByCoinAndAddress.getString("data");
                //调取奖励接口
                rewardAPI.getRewardCenter(coin,accountName,aUserData.getCoinAddress(),balance);
            }

        }
        log.info("========结束执行日结======");
    }

    private Integer createDebitDailyRecord(Integer accountId, String coinAddress,BigDecimal amount,Integer aAutoPayoutsId){
        String type = "Debit_Daily";
        TransacTions transacTions = new TransacTions();
        transacTions.setAccountId(accountId);
        transacTions.setAmount(amount);
        transacTions.setType(type);
        transacTions.setCoinAddress(coinAddress);
        addTransaction(transacTions);
        Integer transactionId = transacTions.getId();
        transacTionService.setArchived(accountId,aAutoPayoutsId);
        return transactionId;
    }
    private String findValue(String configKey){
        return settingMapper.findValueByKey(configKey);
    }

    private void addTransaction(TransacTions transacTions){
         Integer rows = transacTionsMapper.addTransaction(transacTions);
         if(rows != 1){
             log.error("添加日结数据时出现错误!未能成功添加!");
             errorService.insertToPluginsid(ITaskManagerService.DAILY_PLUGINS_ID,"添加日结数据时出现错误!未能成功添加!用户ID:"+transacTions.getAccountId());
             return;
         }
    }
}
