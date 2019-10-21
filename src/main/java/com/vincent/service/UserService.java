package com.vincent.service;

import com.vincent.core.bean.UserInfo;

import java.util.Set;

public interface UserService {
    UserInfo queryUserByUserId(String userId);

    String queryRelam(String userId);

    int addUser(UserInfo userInfo);

    Set<String> queryPermission(String userId);
}
