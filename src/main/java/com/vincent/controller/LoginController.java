package com.vincent.controller;

import com.vincent.core.config.VoiceConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    private VoiceConfig voiceConfig;

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "/register";
    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(String userId, String userPwd) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userPwd)) {
            return "账号或者密码不能为空!";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userId, userPwd);
        try {
            subject.login(token);
            voiceConfig.speak("已成功登陆！" ,userId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/error";
        }
        Session session = subject.getSession();
        session.setAttribute("userId", userId);
        return userId;
    }

    @RequiresRoles(value = "admin")
    @RequestMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @RequiresRoles(value = "user")
    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }
}
