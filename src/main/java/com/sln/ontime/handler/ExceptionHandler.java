package com.sln.ontime.handler;


import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.dto.ResultBean;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description 异常处理器
 * @author guopei
 * @date 2020-01-28 17:46
 */
@Aspect
@Component
@Log4j2
//@ControllerAdvice
public class ExceptionHandler {

//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResultBean handleRequestParameterException(
//            HttpMessageNotReadableException e) {
//        log.info("参数格式错误");
//        return new ResultBean("请输入正确格式的数据", "-1");
//    }


    @Around("execution(public com.sln.ontime.model.dto.ResultBean com.sln.ontime.controller..*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {

        ResultBean<?> result;
        try {
            result = (ResultBean<?>) pjp.proceed();
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        return result;
    }


    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result;
        // 已知异常(业务异常)
        if (e instanceof ErrorException) {
            result = new ResultBean<>((ErrorException) e);
        }
        else {
            // 发生未知异常
            result = new ResultBean<>(e);
            e.printStackTrace();
        }
        return result;
    }

}
