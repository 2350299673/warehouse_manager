package com.pn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 储存用户登录信息的user类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {

    private String userCode;//用户名

    private String userPwd;//密码

    private String userState;//用户状态

    private String verificationCode;//验证码
}
