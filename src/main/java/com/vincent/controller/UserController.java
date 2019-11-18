package com.vincent.controller;

import com.vincent.core.bean.UserInfo;
import com.vincent.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/getUser")
    public String getUser(String userId) {
        UserInfo userInfo = userService.queryUserByUserId(userId);
        return userInfo.toString();
    }

    @Async
    @RequestMapping("/async")
    public long testAsync() {
        log.info("当前线程ID : " + Thread.currentThread().getId());
        return Thread.currentThread().getId();
    }

    @RequestMapping("/addUser")
    public String addUser(UserInfo userInfo) {
        return userService.addUser(userInfo) == 1 ? "SUCESS" : "ERROR";
    }
}
