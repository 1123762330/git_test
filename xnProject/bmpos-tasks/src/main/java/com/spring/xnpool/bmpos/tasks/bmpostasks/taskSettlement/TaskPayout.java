package com.spring.xnpool.bmpos.tasks.bmpostasks.taskSettlement;


import com.alibaba.fastjson.JSON;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.AccountAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.WalletProxyAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.Bitcoin;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.HttpResuletModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SettingsModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SharesForAccountsModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl.HttpAPIService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


import java.math.BigDecimal;
import java.util.*;


@Slf4j
@Controller
public class TaskPayout {
    @Autowired
    private ShareService shareService;

    @Autowired
    AccountAPI accountAPI;

    @Autowired
    WalletProxyAPI walletProxyAPI;

    @Autowired
    private AccountService accountService;


    @Autowired
    private SettingService settingService;

    @Autowired
    Bitcoin bitcoin;

    @Autowired
    private TransacTionService transacTionService;

    @Autowired
    private HttpAPIService httpAPIService;



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

    /**
     * 读取shares 表
     */
    public void tasks_pps_payOut(String coin) throws Throwable {

        Map<String, Object> map = new HashMap();

        map.put("coin", coin);

        //todo 请求获取难度
        JSONObject wallentResult = walletProxyAPI.getDifficulty(coin);
        HttpResuletModel httpResuletModel = JSON.parseObject(wallentResult.toString(), HttpResuletModel.class);
        BigDecimal dDifficulty = null;
        if (httpResuletModel.isSuccess()) {
            dDifficulty = new BigDecimal(httpResuletModel.getData().toString());
        } else {
            log.info("未找到算力");
            return;
        }

        SettingsModel settingsModel = null;

        log.info("dDifficulty 数" + dDifficulty);

        //todo 使用固定的收益计算方式
        SettingsModel settings = settingService.querSettings("pps_reward_default");
        Double pps_reward = Double.valueOf(settings.getValue());

        //todo 根据难度和一个币的自定义价格，计算出pps支付价格
        Double pps_value = bitcoin.calcPPSValue("ltc", pps_reward, dDifficulty);

        log.info("pps 数" + pps_value);

        //todo 获取配置表中记录的上次计算的最后记录位置
        settingsModel = settingService.querSettings("pps_last_share_id");

        Integer iPreviousShareId = 0;
        if (settingsModel == null) {
            iPreviousShareId = 0;
        } else {
            iPreviousShareId = Integer.valueOf(settingsModel.getValue());
        }

        //todo 获取share数据表中最大记录号

        int iLastShareId = shareService.getLastInsertedShareId();

        log.info("setting记录表 pps_last_share_id 数 " + iPreviousShareId + " share数据表最大记录号 " + iLastShareId);

        //判断是否上次计算的最后记录位置 >= 当前最大记录号
        if (iPreviousShareId >= iLastShareId) {
            //没有新数据需要处理 endCronjob 不知道怎么处理
            log.info("没有新数据需要处理，程序后不执行--");
            return;
        } else {
            //todo 数据表shres中获取所有需要处理的数据记录
            List<SharesForAccountsModel> sharesForAccountsModels = shareService.getSharesForAccounts(iPreviousShareId, iLastShareId);

            if (sharesForAccountsModels == null) {
                log.info("查询shares 表 返回为空,不执行操作！");
                return;
            }
            int precision = 0;
            if (sharesForAccountsModels.size() != 0) {
                //从币种配置文件获取精度
                precision = bitcoin.getCoinValuePrevision(coin);

                //table_precision = 精度+3
                int table_precision = precision + 3;
                String strLogMask = "| %-15.15s | %15.15s | %15.15s | %${table_precision}.${table_precision}s | %3.3s |";
                String strLogMaskTwo = "| %8.8s | %25.25s | %15.15s | %${table_precision}.${table_precision}s | " +
                        "%${table_precision}.${table_precision}s | %${table_precision}.${table_precision}s | " +
                        "%${table_precision}.${table_precision}s |";

                //todo 遍历需要处理的数据
                sharesForAccountsModels.forEach(sharesForAccountsModel -> {
                    //todo 根据用户名称去api查询是否存在子账户
                    JSONObject accountResult = accountAPI.getByAidAndCurrency(sharesForAccountsModel.getAccoutName(),coin);
                    //todo 如果返回 不是 200 表示没有 用户
                    if (accountResult.getInt("state") == 200) {
                        String coinAddresses = accountResult.getJSONObject("data").getString("coinAddress");
                        int accountId = accountResult.getJSONObject("data").getInt("accountId");

                        ////根据算力计算支付额度
                        double payout = sharesForAccountsModel.getValid() * pps_value;
                        log.info("支付额度 payout 数" + payout);

                        double fee = 0;
                        double AdatMdonation = 0;
                        //根据配置文件中设置的fees，进行需要交纳费用的计算
                        SettingsModel settingsModeValue = settingService.querSettings("fees");
                        if (Integer.valueOf(settingsModeValue.getValue()) > 0) {
                            SettingsModel settingsModel1 = settingService.querSettings("donation_default");
                            fee = Integer.valueOf(settingsModeValue.getValue()) / 100 * payout;
                            AdatMdonation = Integer.valueOf(settingsModel1.getValue()) / 100 * fee;
                            fee = fee - AdatMdonation;
                        } else {
                            AdatMdonation = 0;
                        }

                        if (coinAddresses != null) {
                            log.info("aDatMDonation 数" + AdatMdonation);
                            //  todo  （这个很重要）交易表中增加一条计算完成的交易记录
                            transacTionService.addTransaction(accountId, payout, coinAddresses, "Credit_PPS");
                            // 如果需要交纳交易费用，需要再增加一条交纳交易费用交易记录
                            if (fee > 0 && Integer.valueOf(settingsModeValue.getValue()) > 0) {
                                transacTionService.addTransaction(accountId, fee, coinAddresses, "Fee_PPS");
                            }
                            //增加捐赠记录
                            if (AdatMdonation > 0 && Integer.valueOf(settings.getValue()) > 0) {
                                transacTionService.addTransaction(accountId, AdatMdonation, coinAddresses, "Credit_PPS");
                            }

                        } else {
                            log.info("账户地址为空，emmmmmmmmmmmmmmm");
                        }
                    } else {
                        log.info(sharesForAccountsModel.getAccoutName() + "获取用户API 失败 ，终止程序");
                        return;
                    }
                });

                //todo 保存开始计算前获取的 最大记录号到配置表，为下次计算做标记
                if (settings.getName() != null) {
                    updateSettingValue("pps_last_share_id", String.valueOf(iLastShareId));
                } else {
                    setValue("pps_last_share_id", String.valueOf(iLastShareId));
                }

            }

        }

    }

    private void updateSettingValue(String pps_last_share_id, String s) {
        SettingsModel settings = new SettingsModel();
        settings.setName(pps_last_share_id);
        settings.setValue(s);
        settingService.updateSettingValue(settings);
    }

    public void setValue(String configKey, String configValue) {
        SettingsModel settings = new SettingsModel();
        settings.setName(configKey);
        settings.setValue(configValue);
        settingService.insert(settings);
    }


}
