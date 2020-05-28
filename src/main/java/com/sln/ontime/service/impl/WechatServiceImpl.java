package com.sln.ontime.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sln.ontime.dao.WechatMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.service.WechatService;
import com.sln.ontime.shiro.token.WechatToken;
import com.sln.ontime.util.RSAUtil;
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

    /**
     * 登录接口
     */
    @Override
    public UserPo login(WechatToken wechatToken) throws Exception {

        log.info("正在执行登录接口");
        //Todo 正式上线需要传头像和昵称
        if (VerifyUtil.isEmpty(wechatToken.getOpenId())) {
            log.info("前端传过来的部分参数为空");
            throw new ErrorException("网络传输异常，请重试");
        }
        //判断数据库内是否已经存在该用户
        UserPo userPo = wechatMapper.getUserByOpenId(wechatToken.getOpenId());
        if (VerifyUtil.isNull(userPo)) {
            //说明还不存在该用户
            log.info("用户{}第一次登录小程序", wechatToken.getName());
            UserVo user = new UserVo();
            user.setName(wechatToken.getName());
            user.setOpenId(wechatToken.getOpenId());
            user.setWechatIcon(wechatToken.getWechatIcon());
            if (wechatMapper.insertUser(user) != 1) {
                log.info("插入用户信息到数据库失败");
                throw new ErrorException("系统出现异常，请稍后重试");
            }
            userPo = wechatMapper.getUserByOpenId(user.getOpenId());
        }
        else if (!userPo.getWechatIcon().equals(wechatToken.getWechatIcon()) ||
                !userPo.getName().equals(wechatToken.getName())) {
            userPo.setName(wechatToken.getName());
            userPo.setWechatIcon(wechatToken.getWechatIcon());
            wechatMapper.updateInfo(userPo);
        }
        log.info("{}登录成功", userPo.getName());
        return userPo;
    }

    /**
     * description 该方法暂时不用
     * @param code
     * @return
     * @throws IOException
     */
//    @Override
//    public UserVo login(String code) throws IOException {
//        if (VerifyUtil.isEmpty(code)) {
//            log.info("用户的code为空，登录失败");
//            return null;
//        }
//        String message = RestTemplateUtil.get(UrlsUtil.getLoginUrl(code));
//        Map<String, Object> result = mapper.readValue(message, Map.class);
//        /*返回的数据集
//        openid	string	用户唯一标识
//        session_key	string	会话密钥
//        unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
//        errcode	number	错误码
//        errmsg	string	错误信息
//        */
//        Integer errcode = (Integer) result.get("errcode");
//        if (errcode == 0) {
//            //说明请求成功
//            log.info("与用户的会话建立成功");
//            String openId = (String) result.get("openid");
//            UserVo userVo = new UserVo();
//            userVo.setOpenId(openId);
//            userVo.setUnionId((String) result.get("unionid"));
//
//            //判断该用户之前是否已经有过记录
//            if (VerifyUtil.isNull(wechatMapper.getUserByOpenId(openId))) {
//                //说明用户之前没有登陆过，需要保存用户的一些基本信息
//                UserPo userPo = new UserPo();
//                wechatMapper.insertUser(userPo);
//            }
//            //Todo 将session_key保存用于后续加解密
//            return userVo;
//        }
//        else {
//            log.info("发送微信端获取openid和session_key失败");
//            log.info("失败原因为{}", result.get("errmsg"));
//            return null;
//        }
//    }





}