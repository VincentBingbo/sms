package com.vincent.core.baseJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

public class BaseDataFlowJob<T> implements DataflowJob<T> {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<T> data) {

    }
}
