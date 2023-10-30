package com.pn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleAuth implements Serializable {

    private int RoleAuthId;
    private int roleId;
    private int authId;
}
