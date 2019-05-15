package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.ErrorEntry;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ErrorMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.InsertException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ErrorServiceImpl implements IErrorService {
    @Autowired
    private ErrorMapper errorMapper;
    private ErrorEntry error = new ErrorEntry();
    @Override
    public void insertError(ErrorEntry error) {
        insert(error);
    }

    @Override
    public void insertToPluginsid(String pluginsId, String message) {
        error.setPluginsId(pluginsId);
        error.setTime(new Date());
        error.setMessage(message);
        insert(error);
    }

    @Override
    public List<Error> getByError(String pluginsId) {
        return fingByError(pluginsId);
    }
    private void insert(ErrorEntry error){
        Integer rows = errorMapper.insert(error);
        if(rows !=1){
            throw new InsertException("添加错误信息时出现未知错误!");
        }
    }
    private List<Error> fingByError(String pluginsId){
        return  errorMapper.findByError(pluginsId);
    }
}
