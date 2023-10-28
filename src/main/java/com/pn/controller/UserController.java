package com.pn.controller;

import com.pn.dto.AssignRoleDto;
import com.pn.page.Page;
import com.pn.pojo.*;
import com.pn.service.AuthService;
import com.pn.service.RoleService;
import com.pn.service.UserRoleService;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    @Autowired
    private AuthService authService;


    //加载权限菜单树
    @RequestMapping("/auth-list")
    public Result authList(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        //拿到当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        //执行业务
        List<Auth> list = authService.listAuthTree(userId);
        //响应
        return Result.ok(list);
    }

    //分页查询用户的url接口/user/user-list

    @RequestMapping("/user-list")
    public Result userList(Page page, User user){

        page = userService.queryUserByPage(page, user);
        return Result.ok(page);
    }

    //注入tokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
            //拿到当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        user.setCreateBy(createBy);

        Result result = userService.queryUser(user);
        return result;
    }

    //用户状态启用和禁用
    @RequestMapping("/updateState")
    public Result updateState(@RequestBody User user){
        Result result = userService.setUserState(user);
        return result;
    }


    @Autowired
    private RoleService roleService;
    //查询用户已分配的角色的url接口/user/user-role-list/{userId}
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId){
        List<Role> roles = roleService.queryUserRoleByUid(userId);
        return Result.ok(roles);
    }


    //给用户分配角色的url接口/user/assignRole
    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto){
        //执行业务
        userService.assignRole(assignRoleDto);
        return Result.ok("分配角色成功");
    }


    //根据用户id去删除用户的url接口
    @RequestMapping("/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable Integer userId){
        //执行业务
        Result result = userService.removeUserByIds(Arrays.asList(userId));
        return result;
    }


    //根据用户ids去批量删除用户的url接口

    @RequestMapping("/deleteUserList")
    public Result deleteUserList(@RequestBody List<Integer> userIdList){
        Result result = userService.removeUserByIds(userIdList);
        return result;
    }


    //根据用户userId修改userName/updateUser

    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录用户的Id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        user.setUpdateBy(updateBy);
        Result result = userService.updateUserName(user);
        return result;
    }

    //重置密码，将用户密码还原成123456(密码加密)/updatePwd/20
    @RequestMapping("/updatePwd/{userId}")
    public Result updatePwd(@PathVariable Integer userId){
        Result result = userService.updateUserPwd(userId);
        return result;
    }


}
