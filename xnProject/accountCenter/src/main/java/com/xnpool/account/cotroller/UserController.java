package com.xnpool.account.cotroller;

import com.xnpool.account.entity.Role;
import com.xnpool.account.entity.User;
import com.xnpool.account.model.HttpResultJsonModel;
import com.xnpool.account.model.UserModel;
import com.xnpool.account.service.impl.RoleService;
import com.xnpool.account.service.impl.UserService;
import com.xnpool.account.util.HttpTokenUtil;
import com.xnpool.account.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("account")
@ResponseBody
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/getUsers")
    public Resp getUsers() {
        List<User> users = userService.getUsers();
        return Resp.success(users);
    }


    @PostMapping("/insertUser")
    public Resp inserUser(UserModel userModel) {

        //添加用户到oauth

        HttpResultJsonModel httpResultJsonModel = HttpTokenUtil.getSign(userModel.getUsername(), userModel.getPhone(), userModel.getPassword());

        if (httpResultJsonModel != null && httpResultJsonModel.getState() == 1) {
            //todo 注册成功 添加到本地数据库
            userService.insertUser(userModel);
            return Resp.success();
        }

        return Resp.fail(httpResultJsonModel.getAlertMessage());
    }

    @PostMapping("/updateRole")
    public Resp updateRole(UserModel userModel) {
        User user = userService.getUSerByID(userModel.getUserid());

        Role role = roleService.getRole(user.getRoleid());

        if (role != null) {
            int updateRole = roleService.updateRoleById(role.getRoleid(), userModel.getRolename(), userModel.getRoleremark());
            if (updateRole > 0) {
                return Resp.success();
            }
        }

        return Resp.fail();
    }

}
