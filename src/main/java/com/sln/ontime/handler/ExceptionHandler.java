package com.sln.ontime.handler;


import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.dto.ResultBean;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @description 异常处理器
 * @author guopei
 * @date 2020-01-28 17:46
 */
@Aspect
@Component
@Log4j2
public class ExceptionHandler {


    @Around("execution(public com.gp.wechart.model.dto.ResultBean com.gp.wechart.controller..*(..))")
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
        } else {
            // 发生未知异常
            result = new ResultBean<>(e);
            e.printStackTrace();
        }
        return result;
    }

}
