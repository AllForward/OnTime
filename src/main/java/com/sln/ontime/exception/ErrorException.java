package com.sln.ontime.exception;


/**
 * @description 抛出的异常类
 * @author guopei
 * @date 2020-01-27 23:05
 */
public class ErrorException extends RuntimeException{

    private static final long serialVersionUID = -7864604160297181941L;

    private final String code;

    /**
     * @Description : 指定枚举类中的错误类
     * @Param : [errorCode]
     */
    public ErrorException(final ErrorExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
    }
    /**
     * @Description : 指定具体业务错误的信息
     * @Param : [detailedMessage]
     */
    public ErrorException(final String message) {
        super(message);
        this.code = ErrorExceptionEnum.SPECIFIED.getCode();
    }

    public String getCode() {
        return code;
    }


}