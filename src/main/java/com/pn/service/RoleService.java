package com.pn.service;

import com.pn.pojo.Role;

import java.util.List;

public interface RoleService {

    //查询所有角色的业务层
    public List<Role> queryAllRole();

    //根据userId查询用户已分配的角色的业务方法
    public List<Role> queryUserRoleByUid(Integer userId);
}
