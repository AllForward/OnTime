package com.sln.ontime.shiro.token;

import com.sln.ontime.model.vo.UserVo;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * @description
 * @author guopei
 * @date 2020-05-01 09:56
 */
@Data
public class WechatToken extends UsernamePasswordToken implements Serializable {


    private UserVo userVo;

    private String code;

    private static final long serialVersionUID = 4812793519945855483L;

    @Override
    public Object getPrincipal() {
        return getUserVo();
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }

    public WechatToken(String code) {
        this.code = code;
    }
}
