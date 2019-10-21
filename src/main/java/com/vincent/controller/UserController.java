package com.vincent.controller;

import com.vincent.core.bean.UserInfo;
import com.vincent.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/getUser")
    public String getUser(String userId) {
        UserInfo userInfo = userService.queryUserByUserId(userId);
        return userInfo.toString();
    }

    @RequestMapping("/addUser")
    public String addUser(UserInfo userInfo) {
        return userService.addUser(userInfo) == 1 ? "SUCESS" : "ERROR";
    }
}
