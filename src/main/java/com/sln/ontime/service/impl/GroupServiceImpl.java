package com.sln.ontime.service.impl;


import com.sln.ontime.dao.GroupMapper;
import com.sln.ontime.dao.MemberMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.Group;
import com.sln.ontime.model.po.Member;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.service.GroupService;
import com.sln.ontime.util.RSAUtil;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description
 * @author guopei
 * @date 2020-05-08 16:45
 */
@Service
@Log4j2
public class GroupServiceImpl implements GroupService {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private GroupMapper groupMapper;

    @Override
    public GroupVo updateMember(MemberVo memberVo, UserPo userPo) throws Exception {

        if (VerifyUtil.isNull(memberVo) || VerifyUtil.isEmpty(memberVo.getGroupId())
                || VerifyUtil.isEmpty(memberVo.getMemberId())) {
            log.info("前端传过来的参数为空");
            throw new ErrorException("网络传输异常，请重试");
        }
        if (!memberVo.getType().equals("add") && !memberVo.getType().equals("delete")) {
            log.info("传输的类型type错误");
            throw new ErrorException("请选择正确的修改成员列表的方式");
        }
        Group group = groupMapper.getGroupByGroupId(memberVo.getGroupId());
        Member member = new Member();
        member.setGroupId(memberVo.getGroupId());
        member.setMemberId(Integer.valueOf(RSAUtil.decrypt(memberVo.getMemberId())));
        //先判断是要删除还是添加成员
        if (memberVo.getType().equals("add")) {
            //说明是添加成员
            //判断下是否已经达到上限
            if (memberMapper.getGroupMemberNum(memberVo.getGroupId()) < group.getLimit()) {
                if (memberMapper.insertMember(member) != 1) {
                    log.info("将id为{}的成员添加到Id为{}的队伍失败", member.getMemberId(), member.getGroupId());
                    throw new ErrorException("服务器异常，请重试");
                }
            }
            else {
                log.info("Id为{}的队伍人员已满", group.getGroupId());
                throw new ErrorException("该团队成员数量已达上限");
            }
        }
        if (memberVo.getType().equals("delete")) {
            //说明是删除成员，需要先校验执行该操作人的身份-->若为群主则可以删除别人||或者本人自己想退出该团队
            if (group.getCreatorId().equals(userPo.getUserId()) || member.getMemberId().equals(userPo.getUserId())) {
                //说明是群或者本人想退群，则有权限
                if (memberMapper.deleteGroupMember(member) != 1) {
                    log.info("将id为{}的成员从Id为{}的队伍剔除失败", member.getMemberId(), member.getGroupId());
                    throw new ErrorException("服务器异常，请重试");
                }
            }
            else {
                log.info("{}的成员无权限将{}的成员从{}的队伍中剔除", userPo.getUserId(), member.getMemberId(), member.getGroupId());
                throw new ErrorException("您没有权限将该成员踢出本团队");
            }
        }
        //Todo 将返回的数据进行完善
        GroupVo groupVo = new GroupVo();





        return null;
    }
}