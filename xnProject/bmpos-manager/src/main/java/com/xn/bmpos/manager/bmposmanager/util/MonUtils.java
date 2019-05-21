package com.xn.bmpos.manager.bmposmanager.util;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class MonUtils {


    public static String big2(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static Map<String, String> getPrice() {
        //存放币价
        Map<String, String> resultMap = new HashMap<>();
        //请求地址
        String priceUrl = "https://rates.blockin.com/v1/rates";
        JSONObject resultJson = null;
        boolean isTrue = true;
        int i = 0;
        try {
            while (isTrue){
                resultJson = JSONObject.fromObject(OkHttpUtils.get(priceUrl, null));
                if (!resultJson.isEmpty() || i >= 100) {
                    isTrue = false;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultJson != null) {
            JSONObject priceArry = resultJson.getJSONObject("price");
            JSONObject ratesArry = resultJson.getJSONObject("rates");
            double rate = ratesArry.getDouble("cny");
            Iterator iterator = priceArry.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Double price = priceArry.getJSONObject(key).getDouble("price");
                DecimalFormat df = new DecimalFormat("#.##");
                resultMap.put(key, df.format(price*rate));
            }
        }
        return resultMap;
    }


}
