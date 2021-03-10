package com.wq.money.system.controller;

import com.wq.money.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(value = "用户管理", tags = {"用户管理"})
@Controller
@RequestMapping(value = "sysUser")
public class SysUserController {

    private final Logger log = LoggerFactory.getLogger(SysUserController.class);

    @ApiOperation(value = "测试页面", notes = "测试页面")
    @GetMapping("index")
    public String test(){
        log.debug("--debug");
        log.info("--info");
        log.warn("--warn");
        log.error("--error");
        return "index.html";
    }

    @ApiOperation(value = "测试数据", notes = "测试数据")
    @GetMapping("getStr")
    @ResponseBody
    public String getStr(){
        return "getStr";
    }


}
