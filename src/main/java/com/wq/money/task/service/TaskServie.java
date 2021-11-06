package com.wq.money.task.service;

import com.wq.money.task.model.form.TaskForm;

public interface TaskServie {
    /**
     * 添加任务
     * @param taskForm
     */
    void addTask(TaskForm taskForm);
    /**
     * 删除任务
     * @param taskForm
     */
    void deleteTask(TaskForm taskForm);
    /**
     * 修改任务
     * @param taskForm
     */
    void updateTask(TaskForm taskForm);
    /**
     * 执行任务
     * @param taskForm
     */
    void runTask(TaskForm taskForm);
    /**
     * 暂停任务
     * @param taskForm
     */
    void stopTask(TaskForm taskForm);
    /**
     * 恢复任务
     * @param taskForm
     */
    void resumeTask(TaskForm taskForm);

}
