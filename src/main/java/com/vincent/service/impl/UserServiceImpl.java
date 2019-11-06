package com.vincent.service.impl;

import com.vincent.core.bean.UserInfo;
import com.vincent.mapper.UserMapper;
import com.vincent.service.UserService;
import com.vincent.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId", cacheNames = "user")
    public UserInfo queryUserByUserId(String userId) {
        log.info("从数据库取数，userId:{}", userId);
        return userMapper.queryUserByUserId(userId);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId", cacheNames = "relam")
    public String queryRelam(String userId) {
        return userMapper.queryRelam(userId);
    }

    @Override
    public int addUser(UserInfo userInfo) {
        String salt = userInfo.getUserName() + UUID.randomUUID().toString();
        userInfo.setSalt(salt);
        userInfo.setRoleId(2);
        userInfo.setIsLock("N");
        userInfo.setCreateTime(new Date());
        userInfo.setStatus("A");
        userInfo.setUpdateTime(new Date());
        SimpleHash simpleHash = new SimpleHash(ShiroUtil.hashAlgorithmName, userInfo.getUserPwd(), salt, ShiroUtil.hashIterations);
        userInfo.setUserPassword(simpleHash.toString());

        return userMapper.addUser(userInfo);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId", cacheNames = "permission")
    public Set<String> queryPermission(String userId) {
        return userMapper.queryPermission(userId);
    }
}
