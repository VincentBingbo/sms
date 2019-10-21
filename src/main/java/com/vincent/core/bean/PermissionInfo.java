package com.vincent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class PermissionInfo {
    private int permissionId;
    private String permissionCode;
    private String permissionDesc;
    private Date createTime;
    private Date updateTime;
}
