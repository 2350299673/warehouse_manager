package com.pn.service.impl;

import com.pn.dto.AssignAuthDto;
import com.pn.mapper.AuthMapper;
import com.pn.mapper.RoleAuthMapper;
import com.pn.mapper.RoleMapper;
import com.pn.page.Page;
import com.pn.pojo.Auth;
import com.pn.pojo.Result;
import com.pn.pojo.Role;
import com.pn.pojo.RoleAuth;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    public Page queryRolePage(Page page, Role role) {
        Integer rowCount = roleMapper.findRoleRowCount(role);

        List<Role> rolePage = roleMapper.findRolePage(page, role);
        page.setTotalNum(rowCount);
        page.setResultList(rolePage);
        return page;
    }

    @CacheEvict(key = "'all:role'")
    @Override
    public Result queryRoleAdd(Role role) {
        Role roleByNameOrCode = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (roleByNameOrCode!=null){
           return Result.err(Result.CODE_ERR_BUSINESS,"角色已存在！");
        }
        int i = roleMapper.insertRole(role);
        if (i>0){
            return Result.ok("添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色添加失败！");
    }

    //启用或禁用角色
    @CacheEvict(key = "'all:role'")//清除缓存
    @Override
    public Result setRoleState(Role role) {
        int i = roleMapper.setRoleStateByRoleId(role.getRoleId(), role.getRoleState());
        if (i>0){
            return Result.ok("角色启用或禁用修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色启用或禁用失败！");
    }

    @CacheEvict(key = "'all:role'")//清除缓存
    @Transactional
    @Override
    public Result deleteRole(Integer roleId) {
        int i = roleMapper.deleteRole(roleId);
        if (i>0){
            //删除角色权限关系
            roleAuthMapper.removeRoleAuthByRid(roleId);
            return Result.ok("删除角色成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除角色失败！");
    }

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    @Override
    public List<Integer> queryAuthByRoleIds(Integer roleId) {
        return roleAuthMapper.findAuthIdsByRid(roleId);
    }

    //给角色分配权限的业务方法
    @Transactional
    @Override
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {
        //删除角色之前分配的所有权限
        roleAuthMapper.removeRoleAuthByRid(assignAuthDto.getRoleId());
        List<Integer> authIds = assignAuthDto.getAuthIds();
        for (Integer authId : authIds) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setRoleId(assignAuthDto.getRoleId());
            roleAuth.setAuthId(authId);
            roleAuthMapper.insertRoleAuth(roleAuth);
        }

    }

}
