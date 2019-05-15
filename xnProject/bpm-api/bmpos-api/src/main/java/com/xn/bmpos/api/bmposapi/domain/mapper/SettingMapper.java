package com.xn.bmpos.api.bmposapi.domain.mapper;

import org.springframework.stereotype.Component;

@Component
public interface SettingMapper {
    String findValueByKey (String name);
}
