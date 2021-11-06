package com.wq.money.task.model.form;

import lombok.Data;

@Data
public class TaskForm {

    private String jobName;

    private String jobGroup;

    private String jobClassPath;

    private String startTime;

    private String endTime;

    private String description;

    private String cronExpression;

}
