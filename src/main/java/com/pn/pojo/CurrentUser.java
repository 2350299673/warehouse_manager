package com.pn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 此user类只封装了用户的用户id、用户名和真实姓名
 */
@Data       //@Data 生成getter,setter ,toString等函数
@NoArgsConstructor      //@NoArgsConstructor 生成无参构造函数
@AllArgsConstructor     //@AllArgsConstructor //生成全参数构造函数
@ToString
public class CurrentUser {
    private int userId;//用户id

    private String userCode;//用户名

    private String userName;//真实姓名
}
