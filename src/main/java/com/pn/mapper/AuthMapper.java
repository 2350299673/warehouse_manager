package com.pn.mapper;

import com.pn.pojo.Auth;

import java.util.List;

public interface AuthMapper {
    //根据userId查询用户权限下的所有菜单
    public List<Auth> findAuthById(Integer userId);
}
