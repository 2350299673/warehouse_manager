package com.pn.mapper;

import com.pn.page.Page;
import com.pn.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * user_info表的Mapper接口
 */
public interface UserMapper {
    //根据账号查询用户信息的方法
    public User findUserByCode(String userCode);

    //查询用户行数的方法
    public Integer findUserRowCount(User user);
    //分页查询用户的方法
    public List<User> findUserByPage(@Param("page") Page page,@Param("user") User user);

    //添加用户的方法
    public int insertUser(User user);

    //启用或禁用用户的方法
    public int updateUserState(User user);

    //根据用户ids修改用户的删除状态的方法
    public int setDeleteByUids(List<Integer> userIdList);

    //根据用户userId修改userName/updateUser
    public int updateUserName(User user);

    //根据用户Id修改用户密码
    public int updateUserPwd(Integer userId,String userPwd);
}
