package com.sln.ontime.controller;

import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.service.impl.SettingServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("updateNickname")
    public ResultBean<?> updateNickname(@RequestParam("nickname")String nickname){
        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        settingService.updateNickname(userPo.getUserId(), nickname);
        return new ResultBean<>();
    }
}
