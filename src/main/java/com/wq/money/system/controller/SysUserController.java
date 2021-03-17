package com.wq.money.system.controller;

import com.wq.money.framework.config.threadPool.ThreadTest;
import com.wq.money.framework.util.email.SendEmail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(value = "用户管理", tags = {"用户管理"})
@Controller
@RequestMapping(value = "sysUser")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SendEmail sendEmail;

    @Resource
    private ThreadTest threadTest;

    @ApiOperation(value = "测试页面", notes = "测试页面")
    @GetMapping("index")
    public String test(){
        logger.debug("--debug");
        logger.info("--info");
        logger.warn("--warn");
        logger.error("--error");
        return "index.html";
    }

    @ApiOperation(value = "测试数据", notes = "测试数据")
    @GetMapping("getStr")
    @ResponseBody
    public String getStr() throws Exception{
        //sendEmail.sendEmail(null); // 发送邮件
        threadTest.test1();  // 线程池
        threadTest.test2();

        return "getStr";
    }
}
