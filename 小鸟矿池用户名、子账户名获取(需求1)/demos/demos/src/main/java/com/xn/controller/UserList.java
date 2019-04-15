package com.xn.controller;

import com.xn.domain.QueryResult;
import com.xn.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserList {
    @Autowired
  private   UserListService userListService;
@RequestMapping(value = "userlist-api" ,method = RequestMethod.GET)
    public QueryResult userlistapi(@RequestParam(value = "last_id" ,required = false) String last_id ){
    return    userListService.findUserListApi(last_id);

}



}
