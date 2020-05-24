package com.sln.ontime.service.impl;

import com.sln.ontime.dao.WechatMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.service.SettingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Red Date.
 * @Description
 * @date 2020/5/24 22:09
 */
@Log4j2
@Service
public class SettingServiceImpl implements SettingService {

    @Resource
    private WechatMapper wechatMapper;

    /**
     * 修改用户的昵称
     *
     * @param userId
     * @param nickname
     */
    @Override
    public void updateNickname(Integer userId, String nickname) {
        if(wechatMapper.updateNickname(nickname,userId)!=1){
            log.error("修改id为{}的用户的昵称为{}失败",userId,nickname);
            throw new ErrorException("服务器异常，请重试");
        }
        log.info("修改id为{}的用户的昵称为{}成功",userId,nickname);
    }
}
