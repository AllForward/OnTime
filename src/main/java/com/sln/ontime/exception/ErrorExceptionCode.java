package com.sln.ontime.exception;


/**
 * @description
 * @author guopei
 * @date 2020-01-28 14:32
 */
public interface ErrorExceptionCode {

    /**
     * @Description : 获取错误代码
     */
    String getCode();

    /**
     * @Description : 获取错误信息
     */
    String getMessage();

}