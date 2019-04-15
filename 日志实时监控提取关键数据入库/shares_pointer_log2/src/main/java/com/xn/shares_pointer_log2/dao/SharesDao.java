package com.xn.shares_pointer_log2.dao;

import com.xn.shares_pointer_log2.domain.Shares;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SharesDao {

    //批量添加数据
    public void insertAll(List<Shares> shares);

    //添加数据
    public void insert(Shares shares);

}
