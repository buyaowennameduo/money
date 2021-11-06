package com.wq.money.task.controller;

import com.wq.money.framework.model.ReturnModel;
import com.wq.money.task.model.form.TaskForm;
import com.wq.money.task.service.TaskServie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(value = "定时任务接口", tags = {"定时任务接口"})
@Controller
@RequestMapping(value = "task")
public class TaskController {

    @Resource
    private TaskServie taskServie;

    @ApiOperation("添加任务")
    @PostMapping(value = "addTask")
    @ResponseBody
    public ReturnModel addTask(@RequestBody TaskForm taskForm){
        taskServie.addTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }
    @ApiOperation("删除任务")
    @PostMapping(value = "deleteTask")
    @ResponseBody
    public ReturnModel deleteTask(@RequestBody TaskForm taskForm){
        taskServie.deleteTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }
    @ApiOperation("修改任务")
    @PostMapping(value = "updateTask")
    @ResponseBody
    public ReturnModel updateTask(@RequestBody TaskForm taskForm){
        taskServie.updateTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }
    @ApiOperation("执行任务")
    @PostMapping(value = "runTask")
    @ResponseBody
    public ReturnModel runTask(@RequestBody TaskForm taskForm){
        taskServie.runTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }
    @ApiOperation("暂停任务")
    @PostMapping(value = "stopTask")
    @ResponseBody
    public ReturnModel stopTask(@RequestBody TaskForm taskForm){
        taskServie.stopTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }
    @ApiOperation("恢复任务")
    @PostMapping(value = "resumeTask")
    @ResponseBody
    public ReturnModel resumeTask(@RequestBody TaskForm taskForm){
        taskServie.resumeTask(taskForm);
        return ReturnModel.newSuccessInstance();
    }

}
