package com.pn.controller;

import com.pn.pojo.Auth;
import com.pn.pojo.Result;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {

    //注入authService
    @Autowired
    private AuthService authService;

    @RequestMapping("/auth-tree")
    public Result authTree(){
        //执行业务
        List<Auth> list = authService.allAuthTree();
        return Result.ok(list);
    }
}
