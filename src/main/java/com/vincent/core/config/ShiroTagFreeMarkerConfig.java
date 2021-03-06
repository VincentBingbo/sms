package com.vincent.core.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Component
public class ShiroTagFreeMarkerConfig implements InitializingBean {
    @Autowired
    private Configuration configuration;
    @Autowired
    private FreeMarkerViewResolver resolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加上 在页面可以使用shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        // 加上 可以在页面上用${context.contextPath}获取contextPath
        resolver.setRequestContextAttribute("context");
    }
}
