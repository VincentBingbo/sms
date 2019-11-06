package com.vincent.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetAddress;

@Component
public class VincentShiroRunner implements ApplicationRunner {
    @Value("${server.port}")
    private int port;
    @Value("${server.servlet.context-path}")
    private String contentPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        String url = String.format("http://%s:%s", address.getHostAddress(), port);
        if (!StringUtils.isEmpty(contentPath)) {
            url += contentPath;
        }
        url += "/login";
//        Runtime.getRuntime().exec("cmd /c start " + url);
    }
}
