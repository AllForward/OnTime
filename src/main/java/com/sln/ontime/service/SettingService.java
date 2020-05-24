package com.sln.ontime.service;

/**
 * @author Red Date.
 * @Description
 * @date 2020/5/24 22:08
 */
public interface SettingService {

    /**
     * 修改用户的昵称
     * @param userId
     * @param nickname
     */
    public void updateNickname(Integer userId,String nickname);
}
