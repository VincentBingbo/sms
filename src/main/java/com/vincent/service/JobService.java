package com.vincent.service;

import com.vincent.core.bean.BatchTaskJob;
import com.vincent.mapper.JobMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobService {
    @Resource
    private JobMapper jobMapper;

    public List<BatchTaskJob> queryActiveTaskJob() {
        return jobMapper.queryActiveTaskJob();
    }

    public int startTask(String taskName) {
        return jobMapper.startTask(taskName);
    }

    public int endTask(String taskName) {
        return jobMapper.endTask(taskName);
    }
}
