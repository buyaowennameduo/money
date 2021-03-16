package com.wq.money.framework.controller;


import com.wq.money.framework.exception.AuthenticationException;
import com.wq.money.framework.exception.BaseException;
import com.wq.money.framework.exception.CheckUniqueFailureException;
import com.wq.money.framework.exception.DataCheckException;
import com.wq.money.framework.model.Code;
import com.wq.money.framework.model.ReturnModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;


@RestController
@ControllerAdvice
public class MyErrorController {

    private static Logger logger = LoggerFactory.getLogger(MyErrorController.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            sb.append(error.getDefaultMessage() + ";");
        }
        logError(req, ex, false);
        return ReturnModel.newFailureInstance(Code.$1101_PARAMETER_ERROR, sb.toString());
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Object bindException(HttpServletRequest req, BindException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(obj -> sb.append(obj.getDefaultMessage() + ";"));

        logError(req, ex, false);

        return ReturnModel.newFailureInstance(Code.$1101_PARAMETER_ERROR, sb.toString());
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Object missingServletRequestParameterErrorHandler(HttpServletRequest req, MissingServletRequestParameterException e) {
        logError(req, e, false);

        return ReturnModel.newFailureInstance(Code.$1101_PARAMETER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = CheckUniqueFailureException.class)
    @ResponseBody
    public Object checkUniqueFailureErrorHandler(HttpServletRequest req, CheckUniqueFailureException e) {
        logError(req, e, false);
        return ReturnModel.newFailureInstance(Code.$1501_DATA_CHECK_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = DataCheckException.class)
    @ResponseBody
    public Object dateCheckFailureErrorHandler(HttpServletRequest req, DataCheckException e) {
        logError(req, e, false);
        return ReturnModel.newFailureInstance(Code.$1501_DATA_CHECK_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseBody
    public Object UsernameNotFoundException(HttpServletRequest req, UsernameNotFoundException e) {
        logError(req, e, false);
        return ReturnModel.newFailureInstance(Code.$1501_DATA_CHECK_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Object AuthenticationException(HttpServletRequest req, AuthenticationException e) throws Exception {
        logError(req, e, false);
        return ReturnModel.newFailureInstance(Code.$1501_DATA_CHECK_ERROR, "用户名或密码错误");
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Object BaseException(HttpServletRequest req, BaseException e) {
        logError(req, e, false);
        if(!StringUtils.isEmpty(e.getErrorMsg())){
            return ReturnModel.newFailureInstance(Code.$1201_SQL_ERROR, e.getMessage(), e.getErrorMsg());
        }
        return ReturnModel.newFailureInstance(Code.$1201_SQL_ERROR, e.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
        logError(req, e, true);
//		res.setStatus(500);
        ReturnModel returnModel = ReturnModel.newFailureInstance(Code.$1000_EXCEPTION, e.getMessage(), "系统错误，请联系管理员");
        return returnModel;
    }

    protected void logError(HttpServletRequest req, Exception e, boolean printStackTrace) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        logger.error(sw.toString());

        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        sb.append("Class {");
        sb.append(e.getClass().getName());
        sb.append("} ");
        sb.append("Host {");
        sb.append(req.getRemoteHost());
        sb.append("} ");
        sb.append("invokes url {");
        sb.append(req.getRequestURL());
        sb.append("} ");
        logger.error(sb.toString());
        if (printStackTrace) {
            logger.error(e.getMessage(), e);
        }
    }

}
