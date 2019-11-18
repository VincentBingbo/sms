package com.vincent.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.vincent.core.baseJob.BaseDataFlowJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class T09M15Job extends BaseDataFlowJob<Map<String, Object>> {

    @Override
    public List<Map<String, Object>> fetchData(ShardingContext shardingContext) {
        log.info(String.format("------Thread ID: %s, 任务总片数: %s, " +
                        "当前分片项: %s.当前參数: %s," +
                        "当前任务名称: %s.当前任务参数: %s",
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("shardingItem", shardingContext.getShardingItem());
        paraMap.put("shardingTotalCount", shardingContext.getShardingTotalCount());
        // 计算唯一键或者联合主键的Hash值，和shardingTotalCount取余，取结果为shardingItem的数据
        // 可以保证数据不会遗漏，也不会重复
        return new ArrayList<Map<String, Object>>();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Map<String, Object>> data) {
        // 这里的data就是fetchData方法返回的数据，得到这些数据后，进行后续的处理
        log.info("获取fetchData()返回的数据，进行后续处理");
    }
}
