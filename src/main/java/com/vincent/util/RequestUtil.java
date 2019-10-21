package com.vincent.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static boolean isAjax(HttpServletRequest httpServletRequest) {
        String request = httpServletRequest.getHeader("x-requested-with");
        if (request != null && "XMLHttpRequest".equalsIgnoreCase(request)) {
            return true;
        }
        return false;
    }
}
