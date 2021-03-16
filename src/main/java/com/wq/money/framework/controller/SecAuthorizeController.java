package com.wq.money.framework.controller;

import com.wq.money.framework.config.security.JwtTokenUtil;
import com.wq.money.framework.config.security.entity.SecUser;
import com.wq.money.framework.config.security.service.SecUserDetailsService;
import com.wq.money.framework.model.ReturnModel;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(value = "登陆接口信息", tags = {"登陆接口信息"})
@Controller
@RequestMapping(value = "auth")
public class SecAuthorizeController {

    private static final Logger log = LoggerFactory.getLogger(SecAuthorizeController.class);

    @Resource
    private SecUserDetailsService secUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "loadPage")
    public String logPage(){
        return "auth/loadPage.html";
    }


    @GetMapping(value = "authSecurityKey")
    @ResponseBody
    public ReturnModel authSecurityKey(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password){
        SecUser user = (SecUser)secUserDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(user);
        return ReturnModel.newSuccessInstance(token);
    }

}
