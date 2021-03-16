package com.wq.money.framework.controller;

import com.wq.money.framework.model.ReturnModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class MyBaseErrorController implements ErrorController {

    @RequestMapping
    @ResponseBody
    public Object handleError(HttpServletRequest request){
        HttpStatus status = getStatus(request);
        return ReturnModel.newFailureInstance(status.value()+"", status.getReasonPhrase(), status.getReasonPhrase());
    }
    @RequestMapping(produces = {"text/html"})
    public Object htmlResult(HttpServletRequest request){
        HttpStatus status = getStatus(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/"+status.value()+".html"); //这里需要在templates文件夹下新建一个/error/500.html文件用作错误页面
        modelAndView.addObject("errorMsg","系统错误");
        return modelAndView;

    }
    @Override
    public String getErrorPath() {
        return null;
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
