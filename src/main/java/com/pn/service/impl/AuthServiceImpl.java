package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.mapper.AuthMapper;
import com.pn.pojo.Auth;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //查询用户菜单树的业务方法
    //向redis缓存用户菜单树 -- 键authTree:userId  值菜单树List<Auth>转的json串

    public List<Auth> listAuthTree(Integer userId) {
        //先从redis中查出用户的权限菜单树
        String authTreeJson = redisTemplate.opsForValue().get("authTree:" + userId);//说明redis中有用户菜单树的缓存
        if (StringUtils.hasText(authTreeJson)){
            //将菜单树List<Auth>转的json串转回菜单树List<Auth>并返回
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }

        //如果redis中没有用户菜单树
        //先查用户权限下的所有菜单
        List<Auth> allAuthList = authMapper.findAuthById(userId);
        //将所有权限菜单转成菜单树
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        //把用户权限下的所有菜单存到redis中
        redisTemplate.opsForValue().set("authTree:" + userId,JSON.toJSONString(authTreeList));
        return authTreeList;

    }

    //通过递归算法把用户权限下的所有菜单转成菜单树
    private List<Auth> allAuthToAuthTree(List<Auth> allAuthTree,Integer pid){
        //创建一个存放一级菜单的集合
        List<Auth> firstLevelAuthTree = new ArrayList<>();
        for (Auth auth : allAuthTree) {
            if (auth.getParentId() == pid){
                firstLevelAuthTree.add(auth);
            }
        }
        //通过一级菜单拿到二级菜单
        for (Auth firstAuth : firstLevelAuthTree) {
            List<Auth> secondAuthList = allAuthToAuthTree(allAuthTree, firstAuth.getAuthId());
            firstAuth.setChildAuth(secondAuthList);
        }
        return firstLevelAuthTree;
    }
}
