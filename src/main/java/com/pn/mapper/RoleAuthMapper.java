package com.pn.mapper;

import com.pn.pojo.Auth;
import com.pn.pojo.RoleAuth;

import java.util.List;

public interface RoleAuthMapper {
    //根据角色id删除角色权限关系的方法
    public int removeRoleAuthByRid(Integer roleId);

    //根据角色id查询角色分配的所有菜单权限id的方法
    public List<Integer> findAuthIdsByRid(Integer roleId);

    //添加角色关系的方法
    public int insertRoleAuth(RoleAuth roleAuth);
}
