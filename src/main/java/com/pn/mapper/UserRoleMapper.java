package com.pn.mapper;


import com.pn.pojo.UserRole;

import java.util.List;

public interface UserRoleMapper {

    //根据用户id删除用户已分配的用户角色关系的方法
    public int removeUserRoleByUid(Integer userId);

    //添加用户角色关系的方法
    public int insertUserRole(UserRole userRole);
}
