package com.wq.money.task.job;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.wq.money.task.service.impl.TaskServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TestJob implements Job {
    private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("定时任务:" + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
    }
}
