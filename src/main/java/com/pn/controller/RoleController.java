package com.pn.controller;

import com.pn.page.Page;
import com.pn.pojo.CurrentUser;
import com.pn.pojo.Result;
import com.pn.pojo.Role;
import com.pn.service.RoleService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    /role/role-list -- 查询所有角色（展示角色出来）

    @RequestMapping("role-list")
    public Result roleList(){
        List<Role> roles = roleService.queryAllRole();
        //响应
        return Result.ok(roles);
    }

    ///role/role-page-list?roleName=&roleCode=&roleState=&pageSize=5&pageNum=1&totalNum=0&_t=1698626651501

    ///role/role-page-list -- 分页查询角色的url接口
    @RequestMapping("/role-page-list")
    public Result pageList(Page page,Role role){
        //执行业务
        page = roleService.queryRolePage(page, role);
        //响应
        return Result.ok(page);
    }
    //注入token对象
    @Autowired
    private TokenUtils tokenUtils;
    //添加角色/role/role-add
    @RequestMapping("role-add")
    public Result roleAdd(@RequestBody Role role,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        role.setCreateBy(userId);

        Result result = roleService.queryRoleAdd(role);
        return result;
    }

    ///role-state-update -- 启用或禁用角色
    @RequestMapping("/role-state-update")
    public Result roleStateUpdate(@RequestBody Role role){
        Result result = roleService.setRoleState(role);
        return result;
    }

    ///role-delete/19 -- 删除角色
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId){
        Result result = roleService.deleteRole(roleId);
        return result;
    }
}
