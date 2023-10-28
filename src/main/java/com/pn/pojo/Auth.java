package com.pn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * auth_info表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

	private int authId;//权限(菜单)id

	private int parentId;//父权限(菜单)id

	private String authName;//权限(菜单)名称

	private String authDesc;//权限(菜单)描述

	private int authGrade;//权限(菜单)层级

	private String authType;//权限(菜单)类型

	private String authUrl;//权限(菜单)访问的url接口

	private String authCode;//权限(菜单)标识

	private int authOrder;//权限(菜单)的优先级

	private String authState;//权限(菜单)状态(1.启用,0.禁用)

	private int createBy;//创建权限(菜单)的用户id

	private Date createTime;//权限(菜单)的创建时间

	private int updateBy;//修改权限(菜单)的用户id

	private Date updateTime;//权限(菜单)的修改时间

	//追加的List<Auth>集合属性 -- 用于存储当前权限(菜单)的子级权限(菜单)
	private List<Auth> childAuth;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Auth auth = (Auth) o;
		return authId == auth.authId && parentId == auth.parentId && authGrade == auth.authGrade && authOrder == auth.authOrder && createBy == auth.createBy && updateBy == auth.updateBy && Objects.equals(authName, auth.authName) && Objects.equals(authDesc, auth.authDesc) && Objects.equals(authType, auth.authType) && Objects.equals(authUrl, auth.authUrl) && Objects.equals(authCode, auth.authCode) && Objects.equals(authState, auth.authState) && Objects.equals(createTime, auth.createTime) && Objects.equals(updateTime, auth.updateTime) && Objects.equals(childAuth, auth.childAuth);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authId, parentId, authName, authDesc, authGrade, authType, authUrl, authCode, authOrder, authState, createBy, createTime, updateBy, updateTime, childAuth);
	}
}
