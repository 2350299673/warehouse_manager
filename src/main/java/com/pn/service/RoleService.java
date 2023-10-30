package com.pn.service;

import com.pn.page.Page;
import com.pn.pojo.Result;
import com.pn.pojo.Role;

import java.util.List;

public interface RoleService {

    //查询所有角色的业务层
    public List<Role> queryAllRole();

    //根据userId查询用户已分配的角色的业务方法
    public List<Role> queryUserRoleByUid(Integer userId);

    //分页查询角色的业务方法
    public Page queryRolePage(Page page,Role role);

    //添加角色
    public Result queryRoleAdd(Role role);

    //根据用户id启用或禁用角色
    public Result setRoleState(Role role);

    //根据用户id删除角色
    public Result deleteRole(Integer roleId);
}
