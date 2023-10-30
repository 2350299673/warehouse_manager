package com.pn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
//接收给角色分配权限请求的json数据的Dto类：
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssignAuthDto {
    //接收角色id
    private Integer roleId;
    //接收给角色分配的所有菜单权限的id
    private List<Integer> authIds;
}
