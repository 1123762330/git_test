package com.xn.bpmuserlistapi.service;

import com.xn.bpmuserlistapi.dao.pojo.QueryResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
public interface TUserService {
    public QueryResult findUserListApi(String lastId);
}
