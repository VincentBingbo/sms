package com.vincent.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class AsyncExecutePoolConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(10);
        executor.setCorePoolSize(2);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("SMS-AsyncTask-");
        executor.setQueueCapacity(20); // 队列长度。
        // 线程池拒绝策略：CallerRunsPolicy：主线程直接执行任务；AbortPolicy: 直接抛出异常。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // 异步任务中 异常处理
        return (throwable, method, objects) -> log.error("异常信息 : " + throwable.getMessage() + " Exception Method : " + method.getName());
    }



}
