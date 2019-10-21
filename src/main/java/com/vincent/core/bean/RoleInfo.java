package com.vincent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class RoleInfo {
    private String roleId;
    private String roleName;
    private Date createDate;
}
