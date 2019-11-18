package com.vincent.core.bean;

import com.dangdang.ddframe.job.api.ElasticJob;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
@Data
@ToString
public class BatchTaskJob implements ElasticJob {
    // 任务主键
    private long taskId;
    // 任务名称
    private String taskName;
    // cron表达式
    private String cron;
    // 分片数
    private int shardingTotalCount;
    // 是否生效
    private String isActive;
    // 任务状态
    private String taskStatus;
    // 创建时间
    private Date createDate;
    // 创建人
    private String createdBy;
    // 更新时间
    private Date updateDate;
    // 更新人
    private String updatedBy;
}
