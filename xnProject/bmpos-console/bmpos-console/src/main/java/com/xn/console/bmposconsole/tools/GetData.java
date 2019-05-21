package com.xn.console.bmposconsole.tools;

import com.xn.console.bmposconsole.dao.mapper.CointypeMapper;
import com.xn.console.bmposconsole.dao.model.CoinType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    private CointypeMapper cointypeMapper;


    //将返回的数据进行封装
    public Object getDataToObject (String coin, Map map) {
        Object data = null;
        String path = request.getServletPath();
        //判断传入的币种是否为空
        if (!StringUtils.isEmpty(coin)) {
            CoinType setting = cointypeMapper.getPluginsByCoin(coin);
            //判断从数据库查询的setting对象是否为空
            if (setting != null) {
                //从数据库获取Ip+请求的路由
                String url = setting.getUrl();
                if(url != null||!"".equals(url)){
                    String rpchost = url + path;
                    //传入参数返回查询的结果
                    String result = OkHttpUtils.get(rpchost, map);
                    //如果结果不为空
                    if (!StringUtils.isEmpty(result)) {
                        //那么就获取data的数据
                        return result;
                    } else {
                        log.info("查询返回的数据为空!");
                    }
                }else {
                    log.info("url为空!币种为:"+coin);
                }

            } else {
                log.info("从数据库查询的币种信息为空");
            }
        } else {
            log.info("传入的币种为空");
        }
        return data;
    }

    //将返回的数据进行封装
    public Object postDataToObject (String coin, Map map) {
        System.err.println("map:"+map);
        Object data = null;
        String path = request.getServletPath();
        //判断传入的币种是否为空
        if (!StringUtils.isEmpty(coin)) {
            CoinType setting = cointypeMapper.getPluginsByCoin(coin);
            //判断从数据库查询的setting对象是否为空
            if (setting != null) {
                //从数据库获取Ip+请求的路由
                String url = setting.getUrl();
                if(url == null){
                    log.info("url为空!币种为:"+coin);
                    return "url为空!币种为:"+coin;
                }
                String rpchost = url + path;
                //传入参数返回查询的结果
                String result = OkHttpUtils.postFormParams(rpchost, map);
                System.err.println(result);
                //如果结果不为空
                if (!StringUtils.isEmpty(result)) {
                    //那么就获取data的数据
                    return result;
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
