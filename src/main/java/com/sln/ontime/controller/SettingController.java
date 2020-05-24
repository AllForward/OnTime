package com.sln.ontime.controller;

import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.service.impl.SettingServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Red Date.
 * @Description
 * @date 2020/5/24 22:06
 */
@RestController
@RequestMapping("/setting")
@Log4j2
public class SettingController {

    @Autowired
    private SettingServiceImpl settingService;
    /**
     * 修改昵称
     * @return
     */
    @PostMapping("updateNickname")
    public ResultBean<?> updateNickname(@RequestParam("userId")Integer userId,@RequestParam("nickname")String nickname){
        settingService.updateNickname(userId,nickname);
        return new ResultBean<>();
    }
}
