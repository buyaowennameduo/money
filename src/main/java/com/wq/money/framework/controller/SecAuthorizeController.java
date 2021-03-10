package com.wq.money.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecAuthorizeController {
    @GetMapping(value = "auth/loadPage")
    public String logPage(){
        return "auth/loadPage.html";
    }
}
