package com.wq.money.task.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.wq.money.task.model.form.TaskForm;
import com.wq.money.task.service.TaskServie;
import lombok.SneakyThrows;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class TaskServiceImpl implements TaskServie {

    private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Resource
    private Scheduler scheduler;

    @Override
    @SneakyThrows
    public void addTask(TaskForm taskForm) {
        Class<? extends Job> jobClass = (Class<? extends Job>)Class.forName(taskForm.getJobClassPath());
        String jobName = taskForm.getJobName();
        String jobGroup = StrUtil.isEmpty(taskForm.getJobGroup()) ? Scheduler.DEFAULT_GROUP : taskForm.getJobGroup();
        Date startTime = DateUtil.parse(taskForm.getStartTime(), DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = DateUtil.parse(taskForm.getEndTime(), DatePattern.NORM_DATETIME_PATTERN);
        String description = taskForm.getDescription();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,jobGroup).withDescription(description).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,jobGroup).startAt(startTime).endAt(endTime)
                .withSchedule(CronScheduleBuilder.cronSchedule(taskForm.getCronExpression()).withMisfireHandlingInstructionDoNothing()).build();
        scheduler.scheduleJob(jobDetail,trigger);
    }

    @Override
    @SneakyThrows
    public void deleteTask(TaskForm taskForm) {
        JobKey jobKey = new JobKey(taskForm.getJobName(), taskForm.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(taskForm.getJobName(), taskForm.getJobGroup());
        scheduler.pauseTrigger(triggerKey);
        scheduler.pauseJob(jobKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
    }

    @Override
    public void updateTask(TaskForm taskForm) {
        deleteTask(taskForm);
        addTask(taskForm);
    }

    @Override
    @SneakyThrows
    public void runTask(TaskForm taskForm) {
        JobKey jobKey = new JobKey(taskForm.getJobName(),taskForm.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    @Override
    @SneakyThrows
    public void stopTask(TaskForm taskForm) {
       TriggerKey triggerKey = new TriggerKey(taskForm.getJobName(),taskForm.getJobGroup());
       scheduler.pauseTrigger(triggerKey);
       JobKey jobKey = new JobKey(taskForm.getJobName(),taskForm.getJobGroup());
       scheduler.pauseJob(jobKey);
    }

    @Override
    @SneakyThrows
    public void resumeTask(TaskForm taskForm) {
        TriggerKey triggerKey = new TriggerKey(taskForm.getJobName(),taskForm.getJobGroup());
        scheduler.resumeTrigger(triggerKey);
        JobKey jobKey = new JobKey(taskForm.getJobName(),taskForm.getJobGroup());
        scheduler.resumeJob(jobKey);
    }
}
