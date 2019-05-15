package com.xn.bmpos.api.bmposapi.service;

import java.util.List;
import java.util.Map;

public interface MyMillService {
    /**
     * 初始化我的矿机参数
     * @param coin
     * @param user
     * @return
     */
    Map<String,Object> getInfo(String coin, String user);

    /**
     * 单台矿机信息
     * @param coin
     * @param millName
     * @return
     */
    Map<String,Object> getOneMillInfo(String coin,String millName);
}
