package com.xn.bmpos.manager.bmposmanager.service;

import com.netflix.discovery.converters.Auto;
import com.xn.bmpos.manager.bmposmanager.domain.dao.Help;
import com.xn.bmpos.manager.bmposmanager.domain.dao.HelpText;
import com.xn.bmpos.manager.bmposmanager.domain.mapper.HelpMapper;
import com.xn.bmpos.manager.bmposmanager.domain.model.HelpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpService {
    @Autowired
    private HelpMapper helpMapper;

    public List<Help> findAll() {
        return helpMapper.selectAll();
    }

    public HelpText SelectTestByPid(int id) {
        //通过pid 寻找t_helpId
        return helpMapper.SelectTestByPid(id);
    }
}
