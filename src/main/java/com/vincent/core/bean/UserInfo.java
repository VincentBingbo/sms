package com.vincent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserInfo {
    private String userId;
    private String userName;
    private String userPwd;
    private String userPassword;
    private String salt;
    private String status;
    private int roleId;
    private String userGender;
    private String userPhone;
    private String isLock;
    private Date createTime;
    private Date updateTime;
}
