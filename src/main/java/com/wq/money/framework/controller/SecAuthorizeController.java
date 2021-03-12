package com.wq.money.framework.controller;

import com.wq.money.framework.config.security.entity.SecUser;
import com.wq.money.framework.config.security.service.SecUserDetailsService;
import com.wq.money.framework.model.ReturnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "auth")
public class SecAuthorizeController {

    private static final Logger log = LoggerFactory.getLogger(SecAuthorizeController.class);

    @Resource
    private SecUserDetailsService secUserDetailsService;

    @GetMapping(value = "loadPage")
    public String logPage(){
        return "auth/loadPage.html";
    }
    @GetMapping(value = "authSecurityKey")
    public ReturnModel authSecurityKey(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password){
        SecUser user = (SecUser)secUserDetailsService.loadUserByUsername(username);
        return ReturnModel.newSuccessInstance(user);
    }

}
