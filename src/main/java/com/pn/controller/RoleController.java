package com.pn.controller;

import com.pn.pojo.Result;
import com.pn.pojo.Role;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
