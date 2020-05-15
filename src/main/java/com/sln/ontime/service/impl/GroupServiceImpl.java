package com.sln.ontime.service.impl;


import com.google.zxing.client.result.BizcardResultParser;
import com.sln.ontime.dao.*;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.Group;
import com.sln.ontime.model.po.Member;
import com.sln.ontime.model.po.Plan;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.service.GroupService;
import com.sln.ontime.util.RSAUtil;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Resource
    private PlanMapper planMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private WechatMapper wechatMapper;

    @Override
    public GroupVo updateMember(MemberVo memberVo, UserPo userPo) throws Exception {

        if (VerifyUtil.isNull(memberVo) || VerifyUtil.isEmpty(memberVo.getGroupId())
                || VerifyUtil.isEmpty(memberVo.getUserId())) {
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
        member.setMemberId(memberVo.getUserId());
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
        groupVo.setGroupId(memberVo.getGroupId());
        groupVo.setGroupName(group.getGroupName());
        List<Plan> planList = planMapper.getPlanByType(memberVo.getGroupId());
        for (Plan plan : planList) {
            plan.setTaskList(taskMapper.getTaskByPlanId(plan.getPlanId()));
        }
        groupVo.setGroupPlanList(planList);
        List<Member> memberList = memberMapper.getMemberByGroupId(memberVo.getGroupId());
        List<MemberVo> memberVoList = new LinkedList<>();
        for (Member m : memberList) {
            MemberVo mv = wechatMapper.getUserByUserId(m.getMemberId());
            memberVoList.add(memberVo);
        }
        groupVo.setGroupMemberList(memberVoList);
        groupVo.setLimit(group.getLimit());
        return groupVo;
    }

    /**
     * 新增团队
     * @param group
     * @param userPo
     * @return
     */
    @Override
    public GroupVo addGroup(Group group, UserPo userPo) {
        if (VerifyUtil.isEmpty(group.getGroupName()) || VerifyUtil.isEmpty(group.getLimit())) {
            log.info("前端传过来的参数不完整");
            throw new ErrorException("请将数据填写完整");
        }
        group.setCreatorId(userPo.getUserId());
        groupMapper.insertGroup(group);
        group = groupMapper.getGroupByGroupId(group.getGroupId());
        GroupVo groupVo = new GroupVo();
        groupVo.setGroupName(group.getGroupName());
        groupVo.setCreatorId(group.getCreatorId());
        groupVo.setLimit(group.getLimit());
        groupVo.setGroupPlanList(null);
        List<Member> memberList = memberMapper.getMemberByGroupId(group.getGroupId());
        List<MemberVo> memberVoList = new ArrayList<>();
        for (Member member :
                memberList) {
            MemberVo memberVo = wechatMapper.getUserByUserId(member.getMemberId());
            memberVoList.add(memberVo);
        }
        groupVo.setGroupMemberList(memberVoList);
        return groupVo;
    }

    /**
     * 删除团队
     * @param groupId
     * @param userPo
     * @return
     */
    @Override
    public String deleteGroup(Integer groupId, UserPo userPo) {
        //判断该团队是否存在
        Group group = groupMapper.getGroupByGroupId(groupId);
        if (group != null) {
            //判断该成员是否为创建者
            if (group.getCreatorId().equals(userPo.getUserId())) {
                groupMapper.deleteGroupByGroupId(groupId);
                return "success";
            }
            else {
                log.info("用户{}无权限删除id为{}的团队", userPo.getName(), groupId);
                throw new ErrorException("您不是该团队创建者,无权限删除该团队");
            }
        }
        else {
            log.info("团队id为{}不存在", groupId);
            throw new ErrorException("该团队不存在");
        }
    }
}