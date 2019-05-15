package com.xn.bmpos.api.bmposapi.service;

import com.github.pagehelper.PageInfo;
import com.xn.bmpos.api.bmposapi.domain.model.Record;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RecordService {

    PageInfo<Record> findCollectionAllRecord (Integer currentPage,Integer pageSize,String address);

    PageInfo<Record> findPayRecord (Integer currentPage,Integer pageSize);
}
