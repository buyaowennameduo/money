package com.wq.money.framework.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "测试", tags = {"测试"})
@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private Logger log1;

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
