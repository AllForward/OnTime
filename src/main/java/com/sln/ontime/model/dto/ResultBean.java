package com.sln.ontime.model.dto;

import com.sln.ontime.exception.ErrorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 返回给前端的统一格式
 * @author guopei
 * @date 2020-01-27 22:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

    // 处理成功的状态码
    public static final String SUCCESS_CODE = "1";
    // 发生未知错误的状态码
    public static final String UNSPECIFIED_CODE = "500";

    public static final String NO_LOGIN = "4000";

    private String message = "success";

    private String code = ResultBean.SUCCESS_CODE;

    private T data;

    public ResultBean(String msg, String code) {
        this.message = msg;
        this.code = code;
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(String code, T data){
        this.code = code;
        this.data = data;
    }

    /**
     * @Description : 此时系统发生未知异常
     * @Param : [e]
     */
    public ResultBean(Throwable e) {
        super();
        this.message = "发生未知错误，请稍后重试!";
        this.code = ResultBean.UNSPECIFIED_CODE;
    }

    public ResultBean(ErrorException e){
        super();
        this.message = e.getMessage();
        this.code = e.getCode();
    }

}
