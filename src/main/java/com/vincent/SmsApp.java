package com.vincent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.vincent.**.mapper")
public class SmsApp {
    public static void main(String[] args) {
        SpringApplication.run(SmsApp.class, args);
    }
}
