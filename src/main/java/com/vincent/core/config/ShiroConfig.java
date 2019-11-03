package com.vincent.core.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.vincent.core.bean.UserInfo;
import com.vincent.service.UserService;
import com.vincent.util.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ShiroConfig {
    @Lazy
    @Resource
    private UserService userService;

    @Bean
    public AuthorizingRealm authorizingRealm() {
        return new AuthorizingRealm() {
            // 授权
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
                if (userInfo == null) return null;
                String roleName = userService.queryRelam(userInfo.getUserId());
                authorizationInfo.addRole(roleName);
                Set<String> permissionSet = userService.queryPermission(userInfo.getUserId());
                authorizationInfo.setStringPermissions(permissionSet);
                return authorizationInfo;
            }

            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                String userId = token.getUsername();
                UserInfo userInfo = userService.queryUserByUserId(userId);
                if (userInfo == null) throw new UnknownAccountException(userId + "此用户不存在！");
                return new SimpleAuthenticationInfo(userInfo, userInfo.getUserPassword(), ByteSource.Util.bytes(userInfo.getSalt()), getName());
            }

            @Override
            public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
                HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
                hashedCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
                hashedCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
                super.setCredentialsMatcher(hashedCredentialsMatcher);
            }
        };
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());
        securityManager.setCacheManager(null);
        securityManager.setSessionManager(getDefaultWebSessionManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/unauthorized");

        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/druid/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/toLogin", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/static/mp3/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/**", "anon");
        filterMap.put("/service/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 30);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
