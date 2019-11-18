package com.vincent.core.listen;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.vincent.service.JobService;
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
    private JobService jobService;

    public SmsElasticJobListener(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        startDt = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        int i = jobService.startTask(getTaskName(shardingContexts.getJobName()));
        log.debug(">>> {} Job Started! <<<", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        Duration duration = Duration.between(startDt, LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        int i = jobService.endTask(getTaskName(shardingContexts.getJobName()));
        log.debug(">>> {} Job Finished! Cost {} ms! <<<", shardingContexts.getJobName(), duration.toMillis());
    }

    private static String getTaskName(String OriTaskName) {
        String[] taskNameArr = OriTaskName.split("\\.");
        if (taskNameArr.length > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(taskNameArr[taskNameArr.length - 1].substring(0, 1).toLowerCase());
            sb.append(taskNameArr[taskNameArr.length - 1].substring(1));
            return sb.toString();
        }
        return null;
    }

}
