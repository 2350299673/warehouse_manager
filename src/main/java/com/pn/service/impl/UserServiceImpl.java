package com.pn.service.impl;

import com.pn.dto.AssignRoleDto;
import com.pn.mapper.RoleMapper;
import com.pn.mapper.UserMapper;
import com.pn.mapper.UserRoleMapper;
import com.pn.page.Page;
import com.pn.pojo.Result;
import com.pn.pojo.User;
import com.pn.pojo.UserRole;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    //根据账号查询用户的业务方法
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    //分页查询用户的业务方法
    @Override
    public Page queryUserByPage(Page page, User user) {
        Integer count = userMapper.findUserRowCount(user);
        List<User> userByPage = userMapper.findUserByPage(page, user);
        page.setTotalNum(count);
        page.setResultList(userByPage);
        System.out.println(userByPage+"---------------------------------");
        return page;
    }

    @Override
    public Result queryUser(User user) {
        User userByCode = userMapper.findUserByCode(user.getUserCode());
        if (userByCode!=null){//账号已存在
            return Result.err(Result.CODE_ERR_BUSINESS,"账号已存在");
        }
        //密码做加密处理
        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        //执行添加操作
        int i = userMapper.insertUser(user);
        if (i>0){
            return Result.ok("用户添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败！");
    }

    //启用或禁用用户的业务方法
    @Override
    public Result setUserState(User user) {
        int i = userMapper.updateUserState(user);
        if (i>0){
            return Result.ok("启用或禁用用户成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"启用或禁用用户失败！");
    }

    //给用户分配角色的方法
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    //方法涉及多条更新 添加事务处理
    @Transactional
    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {
        //先删除用户角色
        int i = userRoleMapper.removeUserRoleByUid(assignRoleDto.getUserId());
            List<String> roleCheckList = assignRoleDto.getRoleCheckList();
            for (String s : roleCheckList) {
                Integer roleId = roleMapper.findRoleIdByName(s);
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(assignRoleDto.getUserId());
                userRoleMapper.insertUserRole(userRole);
        }
    }

    //根据用户ids修改用户的删除状态的方法
    @Override
    public Result removeUserByIds(List<Integer> userIdList) {
        int i = userMapper.setDeleteByUids(userIdList);
        if (i>0){
            return Result.ok("用户删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户删除失败！");
    }

    @Override
    public Result updateUserName(User user) {
        int i = userMapper.updateUserName(user);
        if (i>0){
            return Result.ok("用户名修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户名修改失败！");
    }

    //根据用户id修改用户密码业务
    @Override
    public Result updateUserPwd(Integer userId) {
        //给定初始密码 123456 并加密
        String password = DigestUtil.hmacSign("123456");
        //执行mapper方法
        int i = userMapper.updateUserPwd(userId, password);
        if (i>0){
            return Result.ok("密码重置成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"密码重置失败！");
    }
}
