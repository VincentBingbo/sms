package com.vincent.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.vincent.core.baseJob.BaseSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsSimpleJob extends BaseSimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(">>> {} Job Execute! <<<", shardingContext.getJobName());
        try {
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            log.error("Job Execute Failed!", e);
        }
    }
}
