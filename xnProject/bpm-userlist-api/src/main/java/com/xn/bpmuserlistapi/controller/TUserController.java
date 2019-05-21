package com.xn.bpmuserlistapi.controller;


import com.xn.bpmuserlistapi.dao.pojo.QueryResult;
import com.xn.bpmuserlistapi.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
@RestController
@Slf4j
public class TUserController {
    @Autowired
    private TUserService tUserService;

    @RequestMapping(value = "userlist-api", method = RequestMethod.GET)
    public QueryResult userlistapi (String lastId) {
        QueryResult userListApi = tUserService.findUserListApi(lastId);
        log.info("返回的数据:"+userListApi.toString());
        return tUserService.findUserListApi(lastId);
    }
}

