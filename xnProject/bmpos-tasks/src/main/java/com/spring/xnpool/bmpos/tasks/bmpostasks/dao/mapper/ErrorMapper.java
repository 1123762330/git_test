package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.ErrorEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ErrorMapper {
    Integer insert(ErrorEntry error);
    List<Error> findByError(String pluginsId);


}
