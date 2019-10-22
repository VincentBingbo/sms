package com.vincent.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "/register";
    }

    @RequestMapping("/toLogin")
    public String toLogin(String userId, String userPwd) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userId, userPwd);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/error";
        }

        Session session = subject.getSession();
        session.setAttribute("userId", userId);
        return "/index";
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
