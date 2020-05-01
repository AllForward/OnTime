package com.sln.ontime.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sln.ontime.dao.WechatMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.service.WechatService;
import com.sln.ontime.util.RestTemplateUtil;
import com.sln.ontime.util.UrlsUtil;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;


/**
 * @description
 * @author guopei
 * @date 2020-04-24 16:57
 */
@Log4j2
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private ObjectMapper mapper;

    @Resource
    private WechatMapper wechatMapper;

    @Override
    public UserVo login(String code) throws IOException {
        if (VerifyUtil.isEmpty(code)) {
            log.info("用户的code为空，登录失败");
            return null;
        }
        String message = RestTemplateUtil.get(UrlsUtil.getLoginUrl(code));
        Map<String, Object> result = mapper.readValue(message, Map.class);
        /*返回的数据集
        openid	string	用户唯一标识
        session_key	string	会话密钥
        unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
        errcode	number	错误码
        errmsg	string	错误信息
        */
        Integer errcode = (Integer) result.get("errcode");
        if (errcode == 0) {
            //说明请求成功
            log.info("与用户的会话建立成功");
            String openId = (String) result.get("openid");
            UserVo userVo = new UserVo();
            userVo.setOpenId(openId);
            userVo.setUnionId((String) result.get("unionid"));

            //判断该用户之前是否已经有过记录
            if (VerifyUtil.isNull(wechatMapper.getUserByOpenId(openId))) {
                //说明用户之前没有登陆过，需要保存用户的一些基本信息
                UserPo userPo = new UserPo();
                wechatMapper.insertUser(userPo);
            }
            //Todo 将session_key保存用于后续加解密
            return userVo;
        }
        else {
            log.info("发送微信端获取openid和session_key失败");
            log.info("失败原因为{}", result.get("errmsg"));
            return null;
        }
    }
}