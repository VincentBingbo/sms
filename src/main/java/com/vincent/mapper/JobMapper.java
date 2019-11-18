package com.vincent.mapper;

import com.vincent.core.bean.BatchTaskJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobMapper {
    List<BatchTaskJob> queryActiveTaskJob();
    int startTask(String taskName);
    int endTask(String taskName);
}
