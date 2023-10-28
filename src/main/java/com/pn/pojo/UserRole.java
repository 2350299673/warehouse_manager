package com.pn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole implements Serializable {
    private int userRoleId;
    private int roleId;
    private int userId;
}
