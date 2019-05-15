package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily.DailyVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout.TransactionsVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.TransacTionsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.TransacTionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacTionServiceImpl implements TransacTionService {
    private Logger log = LoggerFactory.getLogger(TransacTionServiceImpl.class);
    @Autowired
    TransacTionsMapper transacTionsMapper;
    @Autowired
    ConfigMapper settingMapper;

    @Override
    public void addTransaction(int account_id, double amount, String address, String type) {
        TransacTions transacTions = new TransacTions();
        transacTions.setAccountId(account_id);
        transacTions.setAmount(BigDecimal.valueOf(amount));
        transacTions.setBlockId(null);
        transacTions.setType(type);
        transacTions.setCoinAddress(null);
        transacTions.setTxid(null);
        transacTions.setCoinAddress(address);
        transacTionsMapper.addTransaction(transacTions);
    }

    @Override
    public List<DailyVO> getAPQueue(Integer limit,Double confirmed) {
        return transacTionsMapper.getAPQueue(limit,confirmed);
    }

    public void setArchivedForDebit_Daily(Integer debitDailyId, Integer id, Integer insertId) {
        Integer rows = transacTionsMapper.setArchivedForDebit_Daily(debitDailyId, id, insertId);
        if (rows != 1) {
            log.error("标记日结出现错误!");
            return;
        }
    }

    public TransactionsVO getBalance(Integer accountId) {
        return transacTionsMapper.getBalance(accountId, Integer.valueOf(findValue("confirmations")));
    }

    public List<TransacTions> getDailyQueue(String type) {
        return transacTionsMapper.getDailyQueue(type);
    }

    public Integer setRPCTxId(Integer transactionId, String rpcTxid) {
        Integer rows = transacTionsMapper.setRPCTxId(transactionId, rpcTxid);
        if (rows != 1) {
            return -1;
        }
        return 1;
    }

    public void setArchived(Integer accountId, Integer id) {
        Integer rows = transacTionsMapper.setArchived(accountId, id);
        if (rows == 1) {
            log.info(accountId + "用户Id 标记成功");
        } else {
            log.info(accountId + "用户Id已标记");
        }


    }

    public Integer createDebitAPRecord(TransacTions transactions) {

        return null;
    }

    private String findValue(String configKey) {
        return settingMapper.findValueByKey(configKey);
    }
}
