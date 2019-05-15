package com.xn.bmpos.api.bmposapi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xn.bmpos.api.bmposapi.domain.mapper.RecordMapper;
import com.xn.bmpos.api.bmposapi.domain.model.Record;
import com.xn.bmpos.api.bmposapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public PageInfo<Record> findCollectionAllRecord (Integer currentPage, Integer pageSize, String address) {
        try {
            if (currentPage == 0 || currentPage == null || pageSize == 0 || pageSize == null) {
                PageHelper.startPage(1, 15);
            } else {
                PageHelper.startPage(currentPage, pageSize);
            }
            List<Record> recordList = recordMapper.findCollectionAllRecord(address);
            PageInfo<Record> pageInfo = new PageInfo<>(recordList);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageInfo<Record> findPayRecord (Integer currentPage, Integer pageSize) {
        try {
            if (currentPage == 0 || currentPage == null || pageSize == 0 || pageSize == null) {
                PageHelper.startPage(1, 15);
            } else {
                PageHelper.startPage(currentPage, pageSize);
            }
            List<Record> recordList = recordMapper.findPayRecord();
            PageInfo<Record> pageInfo = new PageInfo<>(recordList);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
