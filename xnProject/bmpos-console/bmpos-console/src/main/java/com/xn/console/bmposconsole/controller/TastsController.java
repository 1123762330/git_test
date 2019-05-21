package com.xn.console.bmposconsole.controller;

import com.google.gson.Gson;
import com.xn.console.bmposconsole.dao.model.FeignModel;
import com.xn.console.bmposconsole.dao.model.Plugins;
import com.xn.console.bmposconsole.tools.GetData;
import com.xn.console.bmposconsole.tools.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tasks")
public class TastsController {
    @Autowired
    GetData getData;

    /**
     * 获取插件列表
     * @param coin
     * @return
     */
    @GetMapping("/get_list")
    public Resp getPluginsByCoin(String coin){
        Object dataToObject = getData.getDataToObject(coin.toUpperCase(), null);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }
    /**
     * 设置定时间隔
     *
     * @param pid
     * @param unit
     * @param duration
     * @return
     */
    @RequestMapping("/put_thread")
    public Resp setThread(String coin,String pid, String unit, Integer duration) {
        Map<String,Object> map = new HashMap<>();
        map.put("pid",pid);
        map.put("unit",unit);
        map.put("duration",duration);
        Object dataToObject = getData.getDataToObject(coin.toUpperCase(), map);

        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 设置是否激活
     *
     * @param pid
     * @return
     */
    @RequestMapping("/put_active")
    public Resp setActive(String coin,String pid) {
        Map<String,Object> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 停止定时
     *
     * @param pid
     * @return
     */
    @RequestMapping("/stop")
    public Resp setStop(String coin,String pid) {
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 设置插件参数
     *
     * @return
     */
    @RequestMapping("/set")
    public Resp setPlugins(Plugins plugin) {
        Map<String,String> map = new HashMap<>();
        map.put("active",plugin.getActive().toString());
        map.put("unit",plugin.getUnit());
        map.put("duration",plugin.getDuration().toString());
        map.put("category",plugin.getCategory());
        map.put("name",plugin.getName());
        map.put("coin",plugin.getCoin());
        Object dataToObject = getData.getDataToObject(plugin.getCoin(), map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 手动执行
     *
     * @param pid
     * @return
     */
    @RequestMapping("/post_exe")
    public Resp exePlugins(String coin,String pid) {
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }
    /**
     * 设置扫描时间
     * @param unit
     * @param duration
     * @return
     */
    @GetMapping("/set_time")
    public Resp setTimeCron(String coin,String unit, Integer duration) {
        Map<String,String> map = new HashMap<>();
        map.put("unit",unit);
        map.put("duration",duration.toString());
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 修改插件
     * @param plugins
     */
    @PostMapping("/set_plugins")
    public Resp setplugins(Plugins plugins) {
        Map<String,String> map = new HashMap<>();
        String name = plugins.getName();
        if(name != null){
            map.put("name",name);
        }
        Integer active = plugins.getActive();
        if(active != null){
            map.put("active",active.toString());
        }
        String unit = plugins.getUnit();
        if(unit != null){
            map.put("unit",unit);
        }
        Integer duration = plugins.getDuration();
        if(duration != null){
            map.put("duration",duration.toString());
        }
        String category = plugins.getCategory();
        if(category!=null){
            map.put("category",category);
       }
        String coin = plugins.getCoin();
        if(coin != null){
            map.put("coin",coin);
        }
        String pid = plugins.getPid();
        if(pid != null){
            map.put("pid",pid);
       }
        Object dataToObject = getData.getDataToObject(plugins.getCoin(), map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 得到插件信息
     * @param pid
     * @return
     */
    @GetMapping("/get_plugins")
    public Resp getPluginsByid(String coin,String pid) {
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }
}
