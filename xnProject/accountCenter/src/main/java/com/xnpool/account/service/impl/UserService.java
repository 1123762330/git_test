package com.xnpool.account.service.impl;


import com.xnpool.account.entity.Role;
import com.xnpool.account.entity.User;
import com.xnpool.account.mappers.RoleMapper;
import com.xnpool.account.mappers.UserMaper;
import com.xnpool.account.model.HttpResultTokenModel;
import com.xnpool.account.util.AgentUserKit;
import com.xnpool.account.util.GetUserIPUtil;
import com.xnpool.account.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * user
 *
 * @author hjx
 */
@Service
public class UserService {

    @Autowired
    private UserMaper userMaper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LoginHistroyService loginHistroyService;

    public User insertOrUpdateUser(HttpResultTokenModel httpResuletEntity,
                                   String place, HttpServletRequest request) {
        //todo  通过oauth返回的信息去查找本地用户
        String loginIp = GetUserIPUtil.getIpAddr(request);
        User user = getWebUserByResuletOauth(httpResuletEntity);
        if (user != null) {
            user = updateOauthUser(user.getUserid(), httpResuletEntity, loginIp);
        } else {
            //添加
            user = addOauthUser(httpResuletEntity, loginIp);
        }

        //todo 添加历史记录表
        String result = AgentUserKit.getOsAndBrowserInfo(request);
        System.out.println(result);

        loginHistroyService.insertLoginHistroy(user.getUserid(), loginIp, place, result);

        return user;
    }


    private User updateOauthUser(int userid, HttpResultTokenModel httpResuletEntity, String loginIp) {
        User user = null;

        int isUpdate = userMaper.updateUser(httpResuletEntity.getUser_nickname(), httpResuletEntity.getUserEmail(),
                httpResuletEntity.getUserPhone(), loginIp, 1, httpResuletEntity.getIs_app(), userid);

        if (isUpdate != 0) {
            user = getUserByPhone(httpResuletEntity.getUserPhone());
        }
        return user;
    }

    private User addOauthUser(HttpResultTokenModel httpResuletEntity, String loginIp) {

        User user = new User();
        Role role = addRole();
        user.setName(httpResuletEntity.getUser_nickname());
        user.setMobile(httpResuletEntity.getUserPhone());
        user.setEmail(httpResuletEntity.getUserEmail());
        //取當前時間
        user.setCreatetime(new Date());
        user.setRoleid(role.getRoleid());
        user.setIP(loginIp);
        user.setState(1);
        try {
            userMaper.insertUser(user);
        } catch (Exception e) {
            return null;
        }
        return user;

    }

    public User getWebUserByResuletOauth(HttpResultTokenModel httpResuletEntity) {
        User user = null;
        if (StringUtils.isNotEmpty(httpResuletEntity.getUser_nickname())) {
            //todo 用户名
            user = getUserByName(httpResuletEntity.getUser_nickname());
        } else if (StringUtils.isNotEmpty(httpResuletEntity.getUserEmail())) {
            //todo email
            user = getUserByEmail(httpResuletEntity.getUserEmail());
        } else if (StringUtils.isNotEmpty(httpResuletEntity.getUserPhone())) {
            //todo phone
            user = getUserByPhone(httpResuletEntity.getUserPhone());
        }
        return user;
    }

    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    public User getUserByName(String name) {
        return userMaper.getUserByName(name);
    }

    /**
     * 根据用户名查找用户
     *
     * @param mobile
     * @return
     */
    public User getUserByPhone(String mobile) {
        User user = userMaper.getUserByMobile(mobile);
        return user;
    }

    /**
     * 根据用户名查找用户
     *
     * @param email
     * @return
     */
    public User getUserByEmail(String email) {
        return userMaper.getUserByEmail(email);
    }


    public Role addRole() {
        Role role = new Role();
        role.setCreatetime(new Date());
        //默认为普通用户
        role.setRolename("None");
        role.setRoleremark("查询数据");
        roleMapper.insertRole(role);
        return role;
    }

    public void registUser(String username, String phone, String password, String loginIp) {

        User user = new User();
        Role role = addRole();
        user.setName(username);
        user.setMobile(phone);
        user.setRoleid(role.getRoleid());
        String newPwd = Tools.getPassword(username + password);
        user.setPwd(newPwd);
        user.setIP(loginIp);
        userMaper.insertUser(user);
    }

    public void updateUserByEmail(String username, String email) {
        userMaper.updateUserEamilByName(username, email);
    }

    public void updateUserPhone(String username, String phone) {
        userMaper.updateUserPhoneByName(username, phone);
    }

    public void updateUserPassword(String username, String pwd) {
        //密码是由用户米加新密码组成
        String password = Tools.getPassword(username + pwd);
        userMaper.updateUserPasswordByName(username, password);
    }

    //修改用户邮寄地址
    public int updateUserStreet(String username,String street){
        return userMaper.updateUserStreetdByName(username,street);
    }
}
