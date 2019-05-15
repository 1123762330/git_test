package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.StateVO;

public interface IPluginsStateService {
    void setPluginsState(String pluginsId,Integer state);
    StateVO getByPluginsId(String pluginsId);
    Integer findIdByPluginsId(String pluginsId);
}
