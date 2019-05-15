package com.xn.bmpos.api.bmposapi.service;


import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.domain.mapper.MachineMapper;
import com.xn.bmpos.api.bmposapi.domain.model.MachineWebModel;
import com.xn.bmpos.api.bmposapi.tool.RedisUtil;
import com.xn.bmpos.api.bmposapi.tool.Utile;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
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
    private RedisUtil redisUtil;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private HttpAPIService httpAPIService;

    public List<MachineWebModel> querMergeAll() {
        //获取币价
        Map<String, String> resultMap = Utile.getPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        List<MachineWebModel> machineWebModels = machineMapper.queryMachineAll();
        if (machineWebModels.size() > 0) {
            for (MachineWebModel machineWebModel : machineWebModels) {
                //获取矿机算力
                double hashrate = Double.parseDouble(machineWebModel.getHashrate().substring(0, machineWebModel.getHashrate().length() - 1));
                Double earnings = 1.0;
                String coin = machineWebModel.getCoinTypeName();
                String nissan = "";
                switch (coin) {
                    case "BTC":
                        //     日产出           算力     *  实时收益 * 币价                                      汇率
                        nissan = String.valueOf(hashrate * 0.007 * Double.parseDouble(resultMap.get("btc")) * 6.7702);
                        break;
                    case "LTC":                          //单位换算
                        Object str = redisUtil.get("LTC:POOL:STATE");
                        JSONObject object = JSONObject.fromObject(str);

                        Double bockPrice = object.getDouble("bockPrice");
                        Double fee = object.getDouble("fee");
                        Long difficulty = object.getLong("netDifficulty");
                        earnings = GetAVGEstreward(10000, difficulty, bockPrice, fee);
                        nissan = String.valueOf(hashrate * 0.001 * 0.042 * Double.parseDouble(resultMap.get("ltc")) * 6.7702);
                        break;
                    case "DASH":
                        nissan = String.valueOf(hashrate * earnings * Double.parseDouble(resultMap.get("dash")) * 6.7702);
                        break;
                    case "ETH":
                        nissan = String.valueOf(hashrate * 0.092 * Double.parseDouble(resultMap.get("eth")) * 6.7702);
                        break;
                    case "ZEC":
                        nissan = String.valueOf(hashrate * earnings * Double.parseDouble(resultMap.get("zec")) * 6.7702);
                        break;
                    case "DCR":
                        nissan = String.valueOf(hashrate * earnings * Double.parseDouble(resultMap.get("dcr")) * 6.7702);
                        break;
                }
                //日产出
                machineWebModel.setNissan(df.format(new BigDecimal(nissan)));
                //电费
                double electricityBill = machineWebModel.getPower() * 24 * 0.4 * 0.001;
                BigDecimal bgone = new BigDecimal(electricityBill);
                machineWebModel.setElectricityBill(bgone.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费占比 = 电费/日产出
                double electricityProportion = electricityBill / Double.valueOf(nissan);
                machineWebModel.setElectricityProportion(df.format(electricityProportion));
                //净利润 = 日产出 - 电费
                double netProfit = Double.valueOf(nissan) - electricityBill;
                BigDecimal bgtwo = new BigDecimal(netProfit);
                machineWebModel.setNetProfit(bgtwo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        return machineWebModels;
    }

    public List<MachineWebModel> queryMachineAllByCoin(int coinId) {
        //获取币价
        Map<String, String> resultMap = Utile.getPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        List<MachineWebModel> machineWebModels = machineMapper.queryMachineAllByCoin(coinId);
        if (machineWebModels.size() > 0) {
            for (MachineWebModel machineWebModel : machineWebModels) {
                //获取矿机算力
                double hashrate = Double.parseDouble(machineWebModel.getHashrate().substring(0, machineWebModel.getHashrate().length() - 1));
                Double difficult = 0.01;
                String coin = machineWebModel.getCoinTypeName();
                String nissan = String.valueOf(hashrate * difficult * Double.parseDouble(resultMap.get(coin.toLowerCase())) * 6.7702);
                //日产出
                machineWebModel.setNissan(df.format(new BigDecimal(nissan)));
                //电费
                double electricityBill = machineWebModel.getPower() * 24 * 0.4 * 0.001;
                BigDecimal bgone = new BigDecimal(electricityBill);
                machineWebModel.setElectricityBill(bgone.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                //电费占比 = 电费/日产出
                double electricityProportion = electricityBill / Double.valueOf(nissan);
                machineWebModel.setElectricityProportion(df.format(electricityProportion));
                //净利润 = 日产出 - 电费
                double netProfit = Double.valueOf(nissan) - electricityBill;
                BigDecimal bgtwo = new BigDecimal(netProfit);
                machineWebModel.setNetProfit(bgtwo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        return machineWebModels;
    }

    //实时收益                            1000000000
    public static Double GetAVGEstreward(long wei, Long difficulty, Double blockPrice, Double free) {
        Double v = wei * 3600.0 * 24.0 * blockPrice * ((100 - free) / 100) / difficulty;
        return v;
    }

}
