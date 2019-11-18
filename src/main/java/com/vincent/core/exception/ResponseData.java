package com.vincent.core.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseData {
    private int code;
    private Object data;
    private String message;
}
