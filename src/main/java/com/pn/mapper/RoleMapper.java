package com.pn.mapper;

import com.pn.pojo.Role;

import java.util.List;

public interface RoleMapper {

//    查询所有角色的方法
    public List<Role> findAllRole();

    //根据userId查询用户分配的所有角色
    public List<Role> findUserRoleByUid(Integer userId);

    //根据角色查询角色ID
    public Integer findRoleIdByName(String roleName);

}
