package com.xn.bmpos.manager.bmposmanager.util;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

        Map<String, String> resultMap = new HashMap<>();
        String priceUrl = "https://rates.blockin.com/v1/rates";
        JSONObject resultJson = null;
        try {
            resultJson = JSONObject.fromObject(OkHttpUtils.get(priceUrl, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultJson != null) {
            JSONObject priceArry = resultJson.getJSONObject("price");
            Iterator iterator = priceArry.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = priceArry.getJSONObject(key).getString("price");
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }


}
