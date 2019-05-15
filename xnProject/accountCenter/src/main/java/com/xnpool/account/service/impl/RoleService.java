package com.xnpool.account.service.impl;

import com.netflix.discovery.converters.Auto;

import com.xnpool.account.entity.Role;
import com.xnpool.account.entity.User;
import com.xnpool.account.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role getRole(User user) {
        Role role = roleMapper.SelectRoleById(user.getRoleid());
        return role;
    }
}
