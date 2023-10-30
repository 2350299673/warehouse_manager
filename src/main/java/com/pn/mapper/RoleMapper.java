package com.pn.mapper;

import com.pn.page.Page;
import com.pn.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

//    查询所有角色的方法
    public List<Role> findAllRole();

    //根据userId查询用户分配的所有角色
    public List<Role> findUserRoleByUid(Integer userId);

    //根据角色查询角色ID
    public Integer findRoleIdByName(String roleName);

    //查询角色行数的方法
   public Integer findRoleRowCount(Role role);

    //分页查询角色的方法
    public List<Role> findRolePage(@Param("page") Page page,@Param("role") Role role);


    //根据角色名称或角色代码查询角色的方法
    public Role findRoleByNameOrCode(String roleName,String roleCode);

    //添加角色
    public int insertRole(Role role);

    //根据角色Id启用或禁用角色
    public int setRoleStateByRoleId(@Param("roleId") Integer roleId,@Param("roleState")String roleState);

    //根据用户id删除用户角色
    public int deleteRole(Integer roleId);
}
