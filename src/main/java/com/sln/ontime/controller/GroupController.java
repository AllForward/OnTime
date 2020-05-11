package com.sln.ontime.controller;


import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.service.GroupService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author guopei
 * @date 2020-05-08 11:49
 */
@RestController
@Log4j2
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 更新团队成员
     * @param memberVo
     * @return
     */
    @PostMapping("/updateMember")
    public ResultBean<?> updateMember(@RequestBody MemberVo memberVo) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        return new ResultBean<>(groupService.updateMember(memberVo, userPo));
    }

}