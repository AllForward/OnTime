package com.sln.ontime.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.service.WechatService;
import com.sln.ontime.shiro.token.WechatToken;
import com.sln.ontime.util.RSAUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author guopei
 * @date 2020-04-24 16:52
 */
@RestController
@RequestMapping("/wx")
@Log4j2
public class WechatController {

    /**
     * 根据前端传过来的code，后台调用微信端接口获取openId和session_key
     * @return
     */
//    @PostMapping("/login")
//    public ResultBean<?> login(@RequestBody JsonNode jsonNode, HttpServletResponse response,
//                               HttpServletRequest request) {
//        String code = jsonNode.findValue("code").textValue();
//        log.info("用户通过微信登录授权,code为{}", code);
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(new WechatToken(code));
//        UserVo userVo = (UserVo) subject.getPrincipal();
//        response.setHeader("Authorization", subject.getSession().getId().toString());
//        return new ResultBean<>(userVo);
//    }

    /**
     * 用户登录接口
     * @param wechatToken
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResultBean<?> login(@RequestBody WechatToken wechatToken, HttpServletResponse response,
                               HttpServletRequest request) throws Exception {
        log.info("用户通过了微信授权，前端请求后台存储用户数据");
        Subject subject = SecurityUtils.getSubject();
        subject.login(wechatToken);
        UserPo userPo = (UserPo) subject.getPrincipal();
        response.setHeader("Authorization", subject.getSession().getId().toString());
        UserVo userVo = new UserVo();
        userVo.setUserId(RSAUtil.encrypt(String.valueOf(userPo.getUserId())));
        userVo.setWechatIcon(userPo.getWechatIcon());
        userVo.setName(userPo.getName());
        return new ResultBean<>(userVo);
    }

}
