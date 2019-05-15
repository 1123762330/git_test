package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.PluginsState;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.StateVO;
import org.springframework.stereotype.Component;

@Component
public interface PluginsStateMapper {
    Integer insert(PluginsState pluginsState);
    Integer updatebyPluginsId(PluginsState pluginsState);
    StateVO findByPluginsId(String pluginsId);
    PluginsState findIdByPluginsId(String pluginsId);

}
