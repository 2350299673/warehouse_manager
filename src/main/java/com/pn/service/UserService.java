package com.pn.service;

import com.pn.dto.AssignRoleDto;
import com.pn.page.Page;
import com.pn.pojo.Result;
import com.pn.pojo.Role;
import com.pn.pojo.User;

import java.util.List;

/**
 * user_info的service接口
 */
public interface UserService {
    //根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);
    //分页查询用户的业务方法
    public Page queryUserByPage(Page page,User user);
    //添加用户的业务方法
    public Result queryUser(User user);

    //启用或禁用用户的业务方法
    public Result setUserState(User user);

    //给用户分配角色的方法
    public void assignRole(AssignRoleDto assignRoleDto);

    //根据用户ids修改用户的删除状态的方法
    public Result removeUserByIds(List<Integer> userIdList);

    //根据用户id修改用户名
    public Result updateUserName(User user);

    //根据用户id修改用户密码业务
    public Result updateUserPwd(Integer userId);
}
