package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily.DailyVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout.TransactionsVO;

import java.util.List;

public interface TransacTionService {

    void setArchivedForDebit_Daily(Integer debitDailyId, Integer id, Integer insertId);

    TransactionsVO getBalance(Integer accountId);

    List<TransacTions> getDailyQueue(String type);

    Integer setRPCTxId(Integer transactionId, String rpcTxid);

    void setArchived(Integer accountId, Integer id);

    void addTransaction(int account_id, double amount, String address, String type);

    List<DailyVO> getAPQueue(Integer limit,Double confirmed);

}
