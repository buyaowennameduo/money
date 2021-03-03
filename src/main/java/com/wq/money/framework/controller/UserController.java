package com.wq.money.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("index")
    public String test(){
        System.out.println("----");
        return "index.html";
    }
}
