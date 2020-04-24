package com.sln.ontime.exception;


/**
 * @description
 * @author guopei
 * @date 2020-01-28 14:35
 */
public enum  ErrorExceptionEnum implements ErrorExceptionCode{

    // 已指明的异常,在异常使用时message并不返回前端，返回前端的为throw新的异常时指定的message
    SPECIFIED("-1","系统发生异常,请稍后重试"),

    // 常用业务异常
    NO_LOGIN("3001", "用户未进行登录");

    private final String code;

    private final String message;


    ErrorExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}