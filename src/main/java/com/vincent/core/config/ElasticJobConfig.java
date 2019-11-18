package com.vincent.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.vincent.core.baseJob.BaseDataFlowJob;
import com.vincent.core.baseJob.BaseSimpleJob;
import com.vincent.core.bean.BatchTaskJob;
import com.vincent.core.listen.SmsElasticJobListener;
import com.vincent.service.JobService;
import com.vincent.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
public class ElasticJobConfig {
    @Value("${elasticJob.shardingItemParameters}")
    private String shardingItemParameters;

    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Resource
    private DruidDataSource druidDataSource;
    @Resource
    private JobService jobService;

    public LiteJobConfiguration jobConfiguration(final Class jobClass,
                                                 final String cron,
                                                 final int shardingTotalCount) {
        JobTypeConfiguration jobConfig = null;
        if (jobClass.getSuperclass().equals(BaseSimpleJob.class)) {
            jobConfig = new SimpleJobConfiguration(
                    JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters).
                            build(), jobClass.getCanonicalName()
            );
        } else if (jobClass.getSuperclass().equals(BaseDataFlowJob.class)) {
            jobConfig = new DataflowJobConfiguration(
                    JobCoreConfiguration.newBuilder(
                            jobClass.getName(), cron, shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters).build(),
                    jobClass.toString(),
                    true
            );
        } else {
            log.info(">>> 非有效的Job类型：{} <<<", jobClass.toString());
            return null;
        }
        LiteJobConfiguration liteJobConfiguration =
                LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();
        return liteJobConfiguration;
    }

    /**
     * 将作业持久化到DB
     *
     * @return
     */
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(druidDataSource);
    }

    @Bean
    public void initJobScheduler() {
        List<BatchTaskJob> batchTaskJobs = jobService.queryActiveTaskJob();
        SmsElasticJobListener smsElasticJobListener = new SmsElasticJobListener(jobService);
        if (null != batchTaskJobs) {
            for (BatchTaskJob batchTaskJob : batchTaskJobs) {
                Object bean = SpringUtil.getBean(batchTaskJob.getTaskName());
                new SpringJobScheduler(
                        (ElasticJob) bean, registryCenter,
                        jobConfiguration(
                                bean.getClass(),
                                batchTaskJob.getCron(),
                                batchTaskJob.getShardingTotalCount()),
                        smsElasticJobListener
                ).init();
            }
        }
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