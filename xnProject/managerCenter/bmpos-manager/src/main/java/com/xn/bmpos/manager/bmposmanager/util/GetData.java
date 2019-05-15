package com.xn.bmpos.manager.bmposmanager.util;

import com.xn.bmpos.manager.bmposmanager.domain.dao.Setting;
import com.xn.bmpos.manager.bmposmanager.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Slf4j
public class GetData {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SettingService settingService;


    //将返回的数据进行封装
    public Object getDataToObject (String coin, Map map) {
        Object data = null;
        String path = request.getServletPath();
        //判断传入的币种是否为空
        if (!StringUtils.isEmpty(coin)) {
            Setting setting = settingService.selectCoin(coin);
            //判断从数据库查询的setting对象是否为空
            if (setting != null) {
                //从数据库获取Ip+请求的路由
                String rpchost = setting.getRpchost() + path;
                //传入参数返回查询的结果
                String result = OkHttpUtils.get(rpchost, map);
                //如果结果不为空
                if (!StringUtils.isEmpty(result)) {
                    //那么就获取data的数据
                    data = JSONObject.fromObject(result).get("data");
                } else {
                    log.info("查询返回的数据为空!");
                }
            } else {
                log.info("从数据库查询的币种信息为空");
            }
        } else {
            log.info("传入的币种为空");
        }
        return data;
    }
}
