package com.vincent.core.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.script.ScriptJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.vincent.core.listen.SmsElasticJobListener;
import com.vincent.job.BaseSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class ElasticJobConfig {
    @Value("${stockJob.cron}")
    private String cron;
    @Value("${stockJob.shardingTotalCount}")
    private int shardingTotalCount;
    @Value("${stockJob.shardingItemParameters}")
    private String shardingItemParameters;

    @Resource
    private ZookeeperRegistryCenter registryCenter;

    @Bean
    public ElasticJobListener jobListener() {
        return new SmsElasticJobListener();
    }

    public LiteJobConfiguration jobConfiguration(final Class jobClass,
                                                 final String cron,
                                                 final int shardingTotalCount,
                                                 final String shardingItemParameters) {
        JobTypeConfiguration jobConfig = null;
        if (jobClass.isAssignableFrom(SimpleJob.class)) {
            jobConfig = new SimpleJobConfiguration(
                    JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName()
            );
        } else if (jobClass.isAssignableFrom(DataflowJob.class)) {
            jobConfig = new DataflowJobConfiguration(
                    JobCoreConfiguration.newBuilder(
                            jobClass.getName(), cron, shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters).build(),
                    jobClass.toString(),
                    true
            );
        } else if (jobClass.isAssignableFrom(ScriptJob.class)) {
        } else {
            log.info(">>> 非有效的Job类型：{} <<<", jobClass.toString());
        }
        LiteJobConfiguration liteJobConfiguration =
                LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();
        return liteJobConfiguration;
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(BaseSimpleJob simpleJob) {
        SmsElasticJobListener smsElasticJobListener = new SmsElasticJobListener();
        return new SpringJobScheduler(
                simpleJob, registryCenter,
                jobConfiguration(
                        simpleJob.getClass(),
                        cron,
                        shardingTotalCount,
                        shardingItemParameters),
                smsElasticJobListener
        );
    }
}

@Configuration
class ElasticRegCenterConfig {

    @Value("${zk.serverList}")
    private String serverList;
    @Value("${zk.nameSpace}")
    private String nameSpace;

    /**
     * 配置zookeeper
     *
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, nameSpace));
    }

}