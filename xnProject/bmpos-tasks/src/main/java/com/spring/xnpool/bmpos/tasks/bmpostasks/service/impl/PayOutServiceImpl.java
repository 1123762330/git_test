package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;



import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout.TransactionsVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.TransacTionsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.WalletProxyAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.producer.Producer;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayOutServiceImpl implements IPayOutService {
    private Logger log = LoggerFactory.getLogger(PayOutServiceImpl.class);
    @Autowired
    Producer producer;
    @Autowired
    WalletProxyAPI walletProxyAPI;
    @Autowired
    private ConfigMapper settingMapper;
    @Autowired
    private TransacTionsMapper transactionMapper;
    @Autowired
    private TransacTionService transacTionService;
    @Autowired
    private IErrorService errorService;
    @Autowired
    private IPluginsStateService pluginsStateService;
    @Autowired
    HttpAPIService httpAPIService;
    private Map<String,Object> coinMap = new HashMap<>();
    private static String wallentIp;

    @Value("${httpClient.wallet-ip}")
    public void getWallentIp(String wallentIps) {
        wallentIp = wallentIps;
    }


    private static String accountIp;

    @Value("${httpClient.account-ip}")
    public void getaccountIp(String accountIps) {
        accountIp = accountIps;
    }
    @Override
    public void PayOut(String coin) {
        log.info("========开始执行支付======");
        //如果setting表的‘disable_payouts’的值为1
        if ("1".equals(findValue("disable_payouts"))) {
            //抛出控制面板禁用支付异常
            log.error("通过管理面板禁用了支付");
            errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"通过管理面板禁用了支付!");
            pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return;
        }
        //开始支付
        //如果钱包不能连接
        coinMap.put("coin",coin);
        String conn = walletProxyAPI.can_connect(coin).getString("success");
        if(!"true".equals(conn)){
            log.error("未找到可连接的钱包!币种:"+coin);
            return;
        }
        // 检查并查看sendToAddressRPC方法是否可用
        // 这并不测试它是否也能工作!
        //判断sendToAddress命令是否找到
        String help = null;
        help = walletProxyAPI.help(coin).getString("success");
        boolean sendmanyAvailable = "true".equals(help) ? true : false;

        //获取真实钱包余额
        BigDecimal dWalletBalance = new BigDecimal(0);
        JSONObject getrealbalance = walletProxyAPI.getrealbalance(coin);
        if("false".equals(getrealbalance.get("success"))){
            log.error("获取ltc余额为0");
            dWalletBalance = new BigDecimal(0);
        }else {
            Double blanace =(Double)getrealbalance.get("data");
            dWalletBalance = BigDecimal.valueOf(blanace);
        }

        //获取已经日结，未支付记录
        List<TransacTions> aDailyPayouts = transacTionService.getDailyQueue("Debit_Daily");
        if ("1".equals(findValue("disable_auto_payouts"))) {
            log.error("自动支付未开启!");
            errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"自动支付未开启!");
            pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return;
        }

        if (aDailyPayouts == null||aDailyPayouts.isEmpty()) {
            log.error("无日结数据!");
            errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"无日结数据!");
            pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return;
        }

        BigDecimal dAPTotalAmount = BigDecimal.valueOf(0);
        for (TransacTions aDailyData : aDailyPayouts) {
            dAPTotalAmount = dAPTotalAmount.add(aDailyData.getAmount());
        }
        //todo 先注销
       if (dAPTotalAmount.compareTo(dWalletBalance) == 1) {
            log.error("你的钱包不够支付!支付金额:" + dAPTotalAmount + " 钱包金额:" + dWalletBalance);
            errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"你的钱包不够支付!支付金额:" + dAPTotalAmount + " 钱包金额:" + dWalletBalance);
             pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return;
        }
        for (TransacTions aDailyData : aDailyPayouts) {
            Integer transactionId = null;
            String rpcTxid = null;
            //验证钱包地址
            Map<String,Object> isValidMap = new HashMap<>();
            isValidMap.put("coin",coin);
            isValidMap.put("coinAddress",aDailyData.getCoinAddress());
            String isValid = null;
            isValid = walletProxyAPI.validateAddress(coin,aDailyData.getCoinAddress()).getString("success");
            if (!"true".equals(isValid)) {
                log.error("错误的钱包地址:" + aDailyData.getCoinAddress());
                errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"错误的钱包地址:" + aDailyData.getCoinAddress());
                pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                continue;
            }
            //todo 密码未知！
           // client.walletPassphrase("123", 60);
            transactionId = createDebitAPRecord(aDailyData.getAccountId(), aDailyData.getCoinAddress(), aDailyData.getAmount(), aDailyData.getId());
            if (transactionId == -1) {
                errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"创建DebitAPRecord数据失败!用户id:"+aDailyData.getAccountId()+",地址:" +aDailyData.getCoinAddress()+",金额:"+aDailyData.getAmount()+"日结id:"+aDailyData.getId());
                pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                continue;
            }


            //从RPC运行支付，现在用户已完全借记
            if (sendmanyAvailable) {
                BigDecimal amount = aDailyData.getAmount().subtract(BigDecimal.valueOf(Double.valueOf(findValue("txfee_auto"))));
                //发送地址:
                isValidMap.put("amount",amount);

                JSONObject sendToAddress =walletProxyAPI.sendToAddress(coin,aDailyData.getCoinAddress(),amount);
                String reslut = sendToAddress.getString("success");
                if("false".equals(reslut)){
                    log.error(sendToAddress.getString("message"));
                    errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"无法添加RPC事务ID!交易ID:"+aDailyData.getId());
                    pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                    continue;
                }else {
                    rpcTxid = sendToAddress.getString("data");
                }
                //transacTionService.setArchived(aDailyData.getAccountId(),aDailyData.getId());
            }
            //更新我们的事务并添加RPC事务ID
            Integer rows = transacTionService.setRPCTxId(transactionId, rpcTxid);
            if (rpcTxid == null || rows == -1) {
                log.error("无法添加RPC事务ID");
                errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"无法添加RPC事务ID!交易ID:"+aDailyData.getId());
                pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                continue;
            }else {
                //支付成功
                pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,1);
                HashMap<String,Object> transMap = new HashMap<>();
                transMap.put("coin",coin);
                transMap.put("rpcTxid",rpcTxid);

                JSONObject transaction = walletProxyAPI.getTransaction(coin,rpcTxid);
                log.info("====交易结果:=====");
                switch (transaction.getInt("data")){
                    case 0:
                        log.info("支付未确认");break;
                    case -1:
                        log.info("支付存在冲突");break;
                    default:
                        producer.send("paysuccess",aDailyData);
                        System.out.println("支付成功");break;

                }

                log.info("========结束执行支付======");
            }

           /* //我们不运行sendtoaddress，而是稍后运行sendmany
            BigDecimal txfee_auto = new BigDecimal(findValue("txfee_auto"));
            BigDecimal bigDecimal = aDailyData.getAmount();
            BigDecimal result = bigDecimal.subtract(txfee_auto);
            aSendMany.put(aDailyData.getCoinAddress(), result);
            transactionIds.add(transactionId);*/
        }
       /* if ("true".equals(findValue("sendmany_enabled ")) && !sendmanyAvailable && aSendMany != null) {
            String rpcTxid = client.sendMany("", aSendMany);
            for (Integer iTransactionID : transactionIds) {
                Integer rows = transacTionService.setRPCTxId(iTransactionID, rpcTxid);
                if (rpcTxid == null || rows == -1) {
                    log.error("无法添加RPC事务ID");
                    errorService.insertToPluginsid(ITaskManagerService.PAYOUT_PLUGINS_ID,"无法添加RPC事务ID!用户ID:"+iTransactionID);
                    pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                    continue;
                }else {
                    //支付成功
                    pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,1);
                }
            }

        }*/

    }


    private Integer createDebitAPRecord(Integer accountId, String coinAddress, BigDecimal amount, Integer debitDailyId) {
        return createDebitRecord(accountId, coinAddress, amount, "Debit_AP", debitDailyId);
    }

    private Integer createDebitRecord(Integer accountId, String coinAddress, BigDecimal amount, String type, Integer debitDailyId) {
        //从金额中计算并扣除txfee
        String txfee = "Debit_MP".equals(type) ? findValue("txfee_manual") : findValue("txfee_auto");
        //添加借方记录
        TransacTions transacTions = new TransacTions();
        transacTions.setAccountId(accountId);
        transacTions.setAmount(amount);
        transacTions.setType(type);
        transacTions.setCoinAddress(coinAddress);
        Integer rows = transactionMapper.addTransaction(transacTions);
        //获取插入的记录ID，这样我们就可以在最后返回它
        Integer transactionId = transacTions.getId();
        if (rows != 1) {
            log.error("添加记录失败");
            pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return -1;
        }
        //添加TXFee记录
        Integer integer = -1;
        if (Double.valueOf(txfee) > 0) {
            transacTions.setAmount(BigDecimal.valueOf(Double.valueOf(txfee)));
            transacTions.setType("TXFee");
            Integer row = transactionMapper.addTransaction(transacTions);
            integer = transacTions.getId();
            if (row != 1) {
                log.error("添加TXFee记录失败");
                pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
                return -1;
            }
        }
        if (debitDailyId > -1) {
            transacTionService.setArchivedForDebit_Daily(debitDailyId, transactionId, integer);
        } else {
            //标记事务存档
            transacTionService.setArchived(accountId, transactionId);
        }
        //重新检查用户余额，确保它现在是0
        TransactionsVO aBalance = transacTionService.getBalance(accountId);
        if (aBalance == null) {
            log.error("未能取得存款！");
            pluginsStateService.setPluginsState(ITaskManagerService.PAYOUT_PLUGINS_ID,-1);
            return -1;
        }
        if (aBalance.getConfirmed().compareTo(BigDecimal.valueOf(0)) == 1) {
            log.error("用户的余额为:" + aBalance.getConfirmed());
            return -1;
        }
        return transactionId;
    }

    private String findValue(String configKey) {
        return settingMapper.findValueByKey(configKey);
    }


}
