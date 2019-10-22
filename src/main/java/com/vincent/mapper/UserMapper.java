package com.vincent.mapper;

import com.vincent.core.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface UserMapper {
    UserInfo queryUserByUserId(String userId);

    String queryRelam(String userId);

    int addUser(UserInfo userInfo);

    Set<String> queryPermission(String userId);
}
