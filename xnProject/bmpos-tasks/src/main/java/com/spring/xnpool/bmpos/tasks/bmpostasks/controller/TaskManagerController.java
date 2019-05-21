package com.spring.xnpool.bmpos.tasks.bmpostasks.controller;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.taskmanage.Plugins;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.DataNotFoundException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.NotActiveException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ITaskManagerService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.taskmanage.ScheduledForDynamicCron;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskManagerController extends BaskController {


    @Autowired
    ITaskManagerService taskMangerService;
    @Autowired
    ScheduledForDynamicCron scheduledForDynamicCron;


    /**
     * 获得插件列表
     *
     * @return
     */
    @GetMapping("/get_list")
    public ResponseResult<List<Plugins>> TaskInit() {
        List<Plugins> data = scheduledForDynamicCron.getList();
        return new ResponseResult<List<Plugins>>(SUCCESS, data);
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
    public ResponseResult<Void> setThread(String pid, String unit, Integer duration) {
        scheduledForDynamicCron.setThread(pid, unit, duration);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 设置是否激活
     *
     * @param pid
     * @return
     */
    @RequestMapping("/put_active")
    public ResponseResult<Integer> setActive(String pid) {
        Integer active = scheduledForDynamicCron.setActive(pid);
        return new ResponseResult<>(SUCCESS, active);
    }

    /**
     * 停止定时
     *
     * @param pid
     * @return
     */
    @RequestMapping("/stop")
    public ResponseResult<Void> setStop(String pid) {
        Plugins plugins = scheduledForDynamicCron.findPmapBy(pid);
        if (plugins.getActive() == 0) {
            throw new NotActiveException("该插件未激活不能删除");
        }
        scheduledForDynamicCron.stopThread(pid);

        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 设置插件参数
     *
     * @return
     */
    @RequestMapping("/set")
    public ResponseResult<Void> setPlugins(Plugins plugin) {

        Plugins plugins = scheduledForDynamicCron.findPmapBy(plugin.getPid());
        if (plugins == null) {
            throw new DataNotFoundException("插件不存在！");
        }

        if (plugins.getActive() == 1) {
            // stop

            scheduledForDynamicCron.stopThread(plugin.getPid());
        } else if (plugins.getActive() == 0) {

            scheduledForDynamicCron.startThread(plugin.getPid(), plugin.getUnit(), plugin.getDuration());
        }
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 手动执行
     *
     * @param pid
     * @return
     */
    @RequestMapping("/post_exe")
    public ResponseResult<Void> exePlugins(String pid) {
        scheduledForDynamicCron.exePlugins(pid);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 设置扫描时间
     * @param unit
     * @param duration
     * @return
     */
    @GetMapping("/set_time")
    public ResponseResult<Void> setTimeCron(String unit, Integer duration) {
        scheduledForDynamicCron.setTimeCron(unit, duration);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 修改插件
     * @param plugins
     */
    @PostMapping("/set_plugins")
    public ResponseResult<Void> setplugins(Plugins plugins) {
        scheduledForDynamicCron.setPlugins(plugins);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 得到插件信息
     * @param pid
     * @return
     */
    @GetMapping("/get_plugins")
    public ResponseResult<Plugins> getPluginsByid(String pid) {
        Plugins plugins = scheduledForDynamicCron.findPmapBy(pid);
        return new ResponseResult<>(SUCCESS, plugins);
    }

}
