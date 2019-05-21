package com.xn.bmpos.manager.bmposmanager.service;


import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.domain.mapper.MachineMapper;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexCoinModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.MachineWebModel;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:       MachineService
 * Author:         Administrator
 * Date:           2019/4/29 15:52
 * Description:
 */
@Service
public class MachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private CoinSettingService coinSettingService;

    @Autowired
    private CoinTypeService coinTypeService;

    public List<MachineWebModel> querMergeAll(String path) {
        //从数据库中获取币价
        Map<String, String> resultMap = new HashMap<>();
        resultMap = coinTypeService.findAllCoin();

        DecimalFormat df = new DecimalFormat("#.00");

        //查询所有的矿机
        List<MachineWebModel> machineWebModels = machineMapper.queryMachineAll();

        if (machineWebModels.size() > 0) {

            for (MachineWebModel machineWebModel : machineWebModels) {
                //矿机算力
                double hashrate = Double.parseDouble(machineWebModel.getHashrate().substring(0, machineWebModel.getHashrate().length() - 1));
                Long a = getHashrate(hashrate, machineWebModel.getHashrate().substring(machineWebModel.getHashrate().length() - 1));
                machineWebModel.setHs(a);
                machineWebModel.setHashrate(String.valueOf(a));
                //实时收益
                Double earnings = 1.0;
                //日产出
                double nissan = 0.0;
                //根据币种 去查询数据库配置
                CoinSetting setting = coinSettingService.selectCoin(machineWebModel.getCoinTypeName());
                //setting表中数据
                double bockPrice = 0;
                String fee = null;
                //全网难度
                double difficulty = 0;

                if (setting != null) {
                    //发送请求获取redis中数据
                    String str = "";
                    try {
                        str = OkHttpUtils.get(setting.getRpchost() + path, null);
                    } catch (Exception e) {
                        System.out.println("machineService发送okhttp请求出错");
                    }

                    //判断请求是否有返回值
                    if (!StringUtils.isEmpty(str)) {
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(str);
                        com.alibaba.fastjson.JSONArray array = jsonObject.getJSONArray("data");
                        //循环返回值中data
                        for (int i = 0; i < array.size(); i++) {
                            com.alibaba.fastjson.JSONObject value = array.getJSONObject(i);
                            IndexCoinModel indexCoinModel = value.toJavaObject(IndexCoinModel.class);
                            //数据库中 币种       equals         redis中coin
                            if (setting.getName().equals(indexCoinModel.getCoin().toLowerCase())) {
                                //赋值
                                bockPrice = setting.getBlockPrice();
                                fee = setting.getFee();
                                difficulty = indexCoinModel.getNetDifficulty();
                                //计算实时收益
                                earnings = GetAVGEstreward(10000, difficulty, bockPrice, Double.valueOf(fee));
                                if ("ltc".equals(setting.getName())) {
                                    earnings = GetAVGEstreward(1, 11919289.39, 3.9, 4.0);
                                    nissan = hashrate * 0.001 * earnings * 1.4925044 * Double.parseDouble(resultMap.get("ltc"));
                                }
                            }
                        }
                        if (nissan == 0.0) {
                            if ("btc".equals(setting.getName())) {
                                earnings = GetAVGEstreward(1000, 6702169884349.0, 3.0, 2.5);
                                nissan = hashrate * earnings * 1.0374 * Double.parseDouble(resultMap.get("btc"));
                            }
                        }
                    } else {
                        System.err.println("请求为空");
                    }
                }

                if (nissan == 0.0) {
                    nissan = 80.0;
                }
                //日产出
                BigDecimal bd = new BigDecimal(nissan);
                machineWebModel.setNissan(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费
                double electricityBill = machineWebModel.getPower() * 24 * 0.4 * 0.001;
                BigDecimal bgone = new BigDecimal(electricityBill);
                machineWebModel.setElectricityBill(bgone.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费占比 = 电费/日产出
                Double electricityProportion = electricityBill / Double.valueOf(nissan);
                long resultValue = Math.round(electricityProportion * 100);

                String str = df.format(electricityProportion);
                machineWebModel.setElectricityProportion(resultValue);
                //净利润 = 日产出 - 电费
                double netProfit = Double.valueOf(nissan) - electricityBill;
                BigDecimal bgtwo = new BigDecimal(netProfit);
                machineWebModel.setNetProfit(bgtwo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        return machineWebModels;
    }

    public List<MachineWebModel> queryMachineAllByCoin(String path, int coinId) {
        ///获取币价
        Map<String, String> resultMap = new HashMap<>();
        resultMap = coinTypeService.findAllCoin();

        DecimalFormat df = new DecimalFormat("#.00");
        List<MachineWebModel> machineWebModels = machineMapper.queryMachineAllByCoin(coinId);

        if (machineWebModels.size() > 0) {

            for (MachineWebModel machineWebModel : machineWebModels) {
                //矿机算力
                double hashrate = Double.parseDouble(machineWebModel.getHashrate().substring(0, machineWebModel.getHashrate().length() - 1));
                Long a = getHashrate(hashrate, machineWebModel.getHashrate().substring(machineWebModel.getHashrate().length() - 1));
                machineWebModel.setHs(a);
                machineWebModel.setHashrate(String.valueOf(a));
                //实时收益
                Double earnings = 1.0;
                //日产出
                double nissan = 0.0;
                //根据币种 去查询数据库配置
                CoinSetting setting = coinSettingService.selectCoin(machineWebModel.getCoinTypeName());
                //库中bockPrice
                double bockPrice = 0;
                //库中费率
                String fee = null;
                //全网难度
                double difficulty = 0;

                if (setting != null) {
                    //发送请求获取redis中数据
                    String str = "";
                    try {
                        str = OkHttpUtils.get(setting.getRpchost() + path, null);
                    } catch (Exception e) {
                        System.out.println("machineService发送okhttp请求出错");
                    }
                    //判断请求是否有返回值
                    if (!StringUtils.isEmpty(str)) {
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(str);
                        com.alibaba.fastjson.JSONArray array = jsonObject.getJSONArray("data");
                        //循环返回值中data
                        for (int i = 0; i < array.size(); i++) {
                            com.alibaba.fastjson.JSONObject value = array.getJSONObject(i);
                            IndexCoinModel indexCoinModel = value.toJavaObject(IndexCoinModel.class);
                            //数据库中 币种       equals         redis中coin
                            if (setting.getName().equals(indexCoinModel.getCoin().toLowerCase())) {
                                //赋值
                                bockPrice = setting.getBlockPrice();
                                fee = setting.getFee();
                                difficulty = indexCoinModel.getNetDifficulty();
//                                earnings = GetAVGEstreward(10000, difficulty, bockPrice, Double.valueOf(fee));
                                //计算实时收益
                                earnings = GetAVGEstreward(1, 11919289.39, 3.9, 4.0);
                                nissan = hashrate * 0.001 * earnings * 1.4925044 * Double.parseDouble(resultMap.get("ltc"));
                                if ("ltc".equals(setting.getName())) {
                                    earnings = GetAVGEstreward(1, 11919289.39, 3.9, 4.0);
                                    nissan = hashrate * 0.001 * earnings * 1.4925044 * Double.parseDouble(resultMap.get("ltc"));
                                }
                                if (nissan == 0.0) {
                                    if ("btc".equals(setting.getName())) {
                                        earnings = GetAVGEstreward(1000, 6702169884349.0, 3.0, 2.5);
                                        nissan = hashrate * earnings * 1.023 * Double.parseDouble(resultMap.get("btc"));
                                    }
                                }
                            }
                        }
                        if (nissan == 0.0) {
                            if ("btc".equals(setting.getName())) {
                                earnings = GetAVGEstreward(1000, 6702169884349.0, 3.0, 2.5);
                                nissan = hashrate * earnings * 1.023 * Double.parseDouble(resultMap.get("btc"));
                            }
                        }
                    } else {
                        System.err.println("http请求为空");
                    }
                }
                if (nissan == 0.0) {
                    nissan = 80.0;
                }
                //日产出
                BigDecimal bd = new BigDecimal(nissan);
                machineWebModel.setNissan(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费
                double electricityBill = machineWebModel.getPower() * 24 * 0.4 * 0.001;
                long l = Math.round(electricityBill * 100);
                BigDecimal bgone = new BigDecimal(electricityBill);
                machineWebModel.setElectricityBill(bgone.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费占比 = 电费/日产出
                Double electricityProportion = electricityBill / Double.valueOf(nissan);
                long resultValue = Math.round(electricityProportion * 100);
                String str = df.format(electricityProportion);
                machineWebModel.setElectricityProportion(resultValue);
                //净利润 = 日产出 - 电费
                double netProfit = Double.valueOf(nissan) - electricityBill;
                BigDecimal bgtwo = new BigDecimal(netProfit);
                machineWebModel.setNetProfit(bgtwo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        return machineWebModels;
    }

    public int selectCoinName(String coinName) {
        return machineMapper.selectCoinName(coinName);
    }

    //实时收益                            1000000000
    public static Double GetAVGEstreward(long wei, double difficulty, Double blockPrice, Double free) {
        Double v = wei * 3600.0 * 24.0 * blockPrice * ((100 - free) / 100) / difficulty;
        return v;
    }

    //计算算力
    public static long getHashrate(Double hashrate, String str) {
        String[] units = new String[]{"H", "K", "M", "G", "T", "P", "E"};
        int y = 0;
        Double h = hashrate;
        for (int i = 0; i < units.length; i++) {
            if (str.equals(units[i])) {
                y = i;
            }
        }
        for (int i = 0; i < y; i++) {
            hashrate *= 1000;
        }
        return hashrate.longValue();
    }


}
