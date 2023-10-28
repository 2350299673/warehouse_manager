package com.pn.service.impl;

import com.pn.mapper.RoleMapper;
import com.pn.pojo.Role;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
//  2>   指定缓存的前缀(数据保存到redis中的键的前缀,一般值给标注了CacheConfig注解的类的全路径)
@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    //查询所有用户的业务方法
    //redis注解版缓存第三步类上加注解指定缓存的键
    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> queryAllRole() {
        List<Role> allRole = roleMapper.findAllRole();
        return allRole;
    }

    @Override
    public List<Role> queryUserRoleByUid(Integer userId) {
        List<Role> userRoleByUid = roleMapper.findUserRoleByUid(userId);

        return userRoleByUid;
    }
}
