package com.xnpool.account.service.impl;

import com.netflix.discovery.converters.Auto;

import com.xnpool.account.entity.Role;
import com.xnpool.account.entity.User;
import com.xnpool.account.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role getRole(User user) {
        Role role = roleMapper.SelectRoleById(user.getRoleid());
        return role;
    }

    public Role getRole(int RoleId) {
        Role role = roleMapper.SelectRoleById(RoleId);
        return role;
    }

    public int updateRoleById(int roleid, String t_role, String roleremark) {
        return roleMapper.updateRole(t_role, new Date(), roleremark, roleid);
    }
}
