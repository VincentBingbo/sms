package com.vincent.mapper;

import com.vincent.core.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface UserMapper {
    UserInfo queryUserByUserId(String userId);

    String queryRelam(String userId);

    int addUser(UserInfo userInfo);

    Set<String> queryPermission(String userId);
}
