package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.ErrorEntry;

import java.util.List;

public interface IErrorService {
    void insertError(ErrorEntry error);
    void insertToPluginsid(String pluginsId,String message);
    List<Error> getByError(String pluginsId);
}
