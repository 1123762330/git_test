package com.xn.shares_pointer_db.dao;

import com.xn.shares_pointer_db.domain.Shares;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface SharesDao {

    //批量添加数据
    public void insertAll(List<Shares> shares);

    //添加数据
    public void insert(Shares shares);

}
