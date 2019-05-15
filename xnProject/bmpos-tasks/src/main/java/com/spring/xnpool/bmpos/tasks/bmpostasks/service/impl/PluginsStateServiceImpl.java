package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.PluginsState;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.StateVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.PluginsStateMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.DataNotFoundException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.InsertException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.UpdateException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IPluginsStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PluginsStateServiceImpl implements IPluginsStateService {
    @Autowired
    PluginsStateMapper pluginsStateMapper;
    private PluginsState pluginsState = new PluginsState();
    @Override
    /**
     * 设置插件运行成功与失败次数!
     * state = 1 表示 成功
     * state = -1 表示 失败
     */
    public void setPluginsState(String pluginsId,Integer state) {
         PluginsState data = findStateByPluginsId(pluginsId);
         if(data == null){
            //添加
             if(state==1){//成功加一
                pluginsState.setSuccess(1);
                pluginsState.setPluginsId(pluginsId);
             }else {//失败加一
                pluginsState.setFailed(1);
                pluginsState.setPluginsId(pluginsId);
             }
             insert(pluginsState);
         }else {
            //修改
             if(state==1){//成功加一
                 data.setSuccess(data.getSuccess()+1);
             }else {//失败加一
                data.setFailed(data.getFailed()+1);
             }
             updatebyPluginsId(data);
         }

    }

    @Override
    public StateVO getByPluginsId(String pluginsId) {
        return findByPluginsId(pluginsId);
    }

    @Override
    public Integer findIdByPluginsId(String pluginsId) {
        PluginsState data =  findStateByPluginsId(pluginsId);
        if(data == null){
            return -1;
        }
        return data.getId();
    }

    private void insert(PluginsState pluginsState){
        pluginsState.setTime(new Date());
        Integer rows = pluginsStateMapper.insert(pluginsState);
        if(rows!=1){
            throw new InsertException("添加插件状态时出现未知错误!");
        }
    }
    private void updatebyPluginsId(PluginsState pluginsState){
        pluginsState.setTime(new Date());
        Integer rows = pluginsStateMapper.updatebyPluginsId(pluginsState);
        if(rows!=1){
            throw new UpdateException("添加插件状态时出现未知错误!");
        }
    }
    private StateVO findByPluginsId(String pluginsId){
        return pluginsStateMapper.findByPluginsId(pluginsId);
    }
    private PluginsState findStateByPluginsId(String pluginsId){
        return pluginsStateMapper.findIdByPluginsId(pluginsId);
    }

}
