package com.vincent.core.listen;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class SmsElasticJobListener implements ElasticJobListener {

    private static LocalDateTime startDt;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        startDt = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        log.info(">>> {} Job Started! <<<", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        Duration duration = Duration.between(startDt, LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        log.info(">>> {} Job Finished! Cost {} ms! <<<", shardingContexts.getJobName(), duration.toMillis());
    }
}
