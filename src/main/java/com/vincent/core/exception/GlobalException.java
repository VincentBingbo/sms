package com.vincent.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(AuthorizationException e) {
//        log.error(e.getMessage(), e);
        return "unauthorized";
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseData getException(Exception e) {
        ResponseData responseData = new ResponseData();
        if (e instanceof NoHandlerFoundException) {
            responseData.setCode(404);
            responseData.setMessage(e.getMessage());
        } else {
            responseData.setCode(400);
        }
        responseData.setData(null);
        return responseData;
    }
}
