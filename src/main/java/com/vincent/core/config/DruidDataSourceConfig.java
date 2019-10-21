package com.vincent.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.vincent.util.DecryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@Configuration
public class DruidDataSourceConfig {
    @Resource
    private Environment environment;

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.entry.key}")
    private String aesKey;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;
    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.druid.stat-view-servlet.deny}")
    private String deny;
    @Value("${spring.datasource.druid.stat-view-servlet.allow}")
    private String allow;
    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
    private String loginUserName;
    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
    private String loginPassword;
    @Value("${spring.datasource.druid.stat-view-servlet.reset-enable}")
    private String resetEnable;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);

        Arrays.stream(environment.getActiveProfiles()).forEach(str -> {
            log.info(">>>>>> 当前环境：{} <<<<<<", str);
            if ("dev".equals(str) || "prd".equals(str)) {
                druidDataSource.setPassword(DecryptUtil.decrypt(password, aesKey));
            }
        });

        druidDataSource.setUsername(username);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setMinIdle(minIdle);

        return druidDataSource;
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", allow);
        registrationBean.addInitParameter("deny", deny);
        registrationBean.addInitParameter("loginUsername" ,loginUserName);
        Arrays.stream(environment.getActiveProfiles()).forEach(str -> {
            if ("dev".equals(str) || "prd".equals(str)) {
                registrationBean.addInitParameter("loginPassword", DecryptUtil.decrypt(loginPassword, aesKey));
            }
        });
        registrationBean.addInitParameter("resetEnable", resetEnable);
        return registrationBean;
    }

}
