package com.pn.mapper;

import com.pn.pojo.Auth;

import java.util.List;

public interface AuthMapper {
    //根据userId查询用户权限下的所有菜单
    public List<Auth> findAuthById(Integer userId);

    //查询所有的权限菜单
    public List<Auth> findAllAuth();

    //根据角色Id查询分配的所有权限菜单的方法
    public List<Auth> findAuthByRoleId(Integer roleId);
}
