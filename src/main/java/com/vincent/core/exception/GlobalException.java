package com.vincent.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalException {

    /**
     * 无
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(AuthorizationException e) {
//        log.error(e.getMessage(), e);
        return "unauthorized";
    }
}
