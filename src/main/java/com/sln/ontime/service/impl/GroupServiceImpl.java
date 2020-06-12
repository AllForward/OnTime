package com.sln.ontime.service.impl;


import com.google.zxing.client.result.BizcardResultParser;
import com.sln.ontime.dao.*;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.*;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.model.vo.PlanVo;
import com.sln.ontime.service.GroupService;
import com.sln.ontime.util.RSAUtil;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
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
                || VerifyUtil.isEmpty(memberVo.getUserId()) || VerifyUtil.isEmpty(memberVo.getType())) {
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
        List<UserPo> memberVoList = new LinkedList<>();
        for (Member m : memberList) {
            UserPo user = wechatMapper.getUserByUserId(m.getMemberId());
            memberVoList.add(user);
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
        //将创建者本人加入到该团队中
        memberMapper.insertMember(new Member(group.getGroupId(), userPo.getUserId()));
        group = groupMapper.getGroupByGroupId(group.getGroupId());
        GroupVo groupVo = new GroupVo();
        groupVo.setGroupId(group.getGroupId());
        groupVo.setGroupName(group.getGroupName());
        groupVo.setCreatorId(group.getCreatorId());
        groupVo.setLimit(group.getLimit());
        groupVo.setGroupPlanList(null);
        List<Member> memberList = memberMapper.getMemberByGroupId(group.getGroupId());
        List<UserPo> memberVoList = new ArrayList<>();
        for (Member member :
                memberList) {
            UserPo user = wechatMapper.getUserByUserId(member.getMemberId());
            memberVoList.add(user);
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

    /**
     * 获取用户所在的团队列表
     * @param userId
     * @return
     */
    @Override
    public List<GroupVo> getGroupList(Integer userId) {
        List<Member> memberList = memberMapper.getGroupsByMemberId(userId);
        if (VerifyUtil.isEmpty(memberList)) {
            log.info("该用户暂未加入任务团队");
            return null;
        }
        List<GroupVo> groupVoList = new ArrayList<>();
        for (Member member : memberList) {
            GroupVo groupVo = new GroupVo();
            BeanUtils.copyProperties(groupMapper.getGroupByGroupId(member.getGroupId()) ,groupVo);
            List<UserPo> groupMemberList = new ArrayList<>();
            for (Member m : memberMapper.getMemberByGroupId(member.getGroupId())) {
                UserPo user = wechatMapper.getUserByUserId(m.getMemberId());
                groupMemberList.add(user);
            }
            groupVo.setGroupMemberList(groupMemberList);
            groupVoList.add(groupVo);
        }
        return groupVoList;
    }

    @Override
    public List<Plan> getListGroupPlan(UserPo userPo) {
        //获取用户的所有团队
        List<Member> memberList = memberMapper.getGroupsByMemberId(userPo.getUserId());
        if (VerifyUtil.isEmpty(memberList)) {
            log.info("用户{}还没有加入任何团队", userPo.getUserId());
            return null;
        }
        List<Plan> listGroupPlan = new ArrayList<>();
        for (Member member : memberList) {
            //获取每个团队的信息
            Group group = groupMapper.getGroupByGroupId(member.getGroupId());
            List<Plan> planList = planMapper.getPlanByType(member.getGroupId());
            if (VerifyUtil.isEmpty(planList)) {
                log.info("团队{}暂无计划", member.getGroupId());
                continue;
            }
            for (Plan plan : planList) {
                List<Task> taskList = taskMapper.getTaskByPlanId(plan.getPlanId());
                if (VerifyUtil.isEmpty(taskList)) {
                    plan.setTaskList(null);
                    plan.setGroupName(group.getGroupName());
                    continue;
                }
                for (Task task : taskList) {
                    task.setUserVo(wechatMapper.getUserByUserId(task.getUserId()));
                }
                plan.setTaskList(taskList);
                plan.setGroupName(group.getGroupName());
            }
            listGroupPlan.addAll(planList);
        }
        return listGroupPlan;
    }

    @Override
    public List<Plan> getGroupPlan(Integer groupId, UserPo userPo) {
        if (VerifyUtil.isEmpty(groupId)) {
            log.info("团队id为空");
            throw new ErrorException("请选择要查询的团队");
        }
        //查看该团队是否存在
        Group group = groupMapper.getGroupByGroupId(groupId);
        if (VerifyUtil.isNull(group)) {
            log.info("id为{}的团队不存在", groupId);
            throw new ErrorException("该团队不存在");
        }
        //判断该成员是否在该团队中
        List<Member> memberList = memberMapper.getMemberByGroupId(groupId);
        for (Member member : memberList) {
            //说明该成员在团队中
            if (member.getMemberId().equals(userPo.getUserId())) {
                List<Plan> planList = planMapper.getPlanByType(groupId);
                if (VerifyUtil.isEmpty(planList)) {
                    log.info("暂无团队计划");
                    return null;
                }
                for (Plan plan : planList) {
                    List<Task> taskList = taskMapper.getTaskByPlanId(plan.getPlanId());
                    for (Task task : taskList) {
                        task.setUserVo(wechatMapper.getUserByUserId(task.getUserId()));
                    }
                    plan.setTaskList(taskList);
                    plan.setGroupName(group.getGroupName());
                }
                return planList;
            }
        }
        log.info("id为{}的用户不属于id为{}的团队", userPo.getUserId(), groupId);
        throw new ErrorException("您还不是该团队的成员，无权访问");
    }

    /**
     * 添加团队计划以及修改团队计划接口（若为添加团队计划，则planId为空，若为修改团队计划，则planId不能为空）
     * @param planVo
     * @return
     */
    @Override
    public PlanVo addGroupPlan(PlanVo planVo) {
        //先对数据进行校验
        if (VerifyUtil.isNull(planVo) || VerifyUtil.isEmpty(planVo.getPlanName()) || VerifyUtil.isEmpty(planVo.getType())) {
            log.info("前端传过来的参数为空");
            throw new ErrorException("请填写要添加的计划");
        }
        //校验该成员是否在团队中
        groupPermission(planVo.getType(), planVo.getUserId());
        //说明是新的大计划(需要先插入)
        log.info("即将插入一条新的大计划");
        Plan plan = new Plan();
        plan.setType(planVo.getType());
        BeanUtils.copyProperties(planVo, plan);
        if(planMapper.insertPlan(plan) != 1){
            log.info("个人计划插入数据库失败,可能部分字段为空");
            throw new ErrorException("系统出现异常，请稍后重试");
        }
        List<Task> taskList = planVo.getTaskList();
        if (!VerifyUtil.isEmpty(taskList)) {
            List<Task> result = new ArrayList<>();
            for (Task task : taskList){
                task.setPlanId(plan.getPlanId());
                task.setUserId(planVo.getUserId());
                if(taskMapper.insertTask(task) != 1){
                    log.info("子任务插入数据库失败");
                    throw new ErrorException("系统出现异常，请稍后重试");
                }
                result.add(task);
            }
            planVo.setPlanId(plan.getPlanId());
            planVo.setTaskList(result);
        }
        return planVo;

    }

    @Override
    public PlanVo updateGroupPlan(PlanVo planVo) {
        //先对数据进行校验
        if (VerifyUtil.isNull(planVo) || VerifyUtil.isEmpty(planVo.getPlanName()) || VerifyUtil.isEmpty(planVo.getType()
        ) || VerifyUtil.isEmpty(planVo.getPlanId())) {
            log.info("前端传过来的参数为空");
            throw new ErrorException("请填写要添加的计划");
        }
        groupPermission(planVo.getType(), planVo.getUserId());
        //可能是修改团队大计划
        log.info("即将对id为{}的大计划名称进行修改", planVo.getPlanId());
        planMapper.updatePlanByPlanId(planVo.getPlanName(), planVo.getPlanId());
        List<Task> taskList = planVo.getTaskList();
        if (!VerifyUtil.isEmpty(taskList)) {
            List<Task> result = new ArrayList<>();
            for (Task task : taskList) {
                task.setPlanId(planVo.getPlanId());
                task.setUserId(planVo.getUserId());
                if (taskMapper.updateTask(task) != 1) {
                    log.info("子任务更新数据库失败");
                    throw new ErrorException("系统出现异常，请稍后重试");
                }
                result.add(task);
            }
            planVo.setTaskList(result);
        }
        else {
            planVo.setTaskList(taskMapper.getTaskByPlanId(planVo.getPlanId()));
        }
        return planVo;
    }

    @Override
    public boolean deleteGroupPlan(Integer planId, UserPo userPo) {
        if (VerifyUtil.isEmpty(planId)) {
            log.info("前端的planId参数为空");
            throw new ErrorException("请选择要删除的团队计划");
        }
        Plan plan = planMapper.getPlanByPlanId(planId);
        if (VerifyUtil.isNull(plan)) {
            log.info("id为{}的大计划不存在", planId);
            throw new ErrorException("该团队计划不存在");
        }
        if (plan.getUserId().equals(userPo.getUserId())
                || userPo.getUserId().equals(groupMapper.getGroupByGroupId(plan.getType()).getCreatorId())) {
            //说明该计划是用户创建的或者是团队创建者
            planMapper.deletePlanByPlanId(planId);
            return true;
        }
        else {
            log.info("id为{}的大计划不是用户{}所创建的", planId, userPo.getUserId());
            throw new ErrorException("该计划不是您创建的，您无权限删除");
        }
    }


    //团队权限校验方法
    private Boolean groupPermission(Integer groupId, Integer userId) {
        //查看该团队是否存在
        if (VerifyUtil.isNull(groupMapper.getGroupByGroupId(groupId))) {
            log.info("id为{}的团队不存在", groupId);
            throw new ErrorException("该团队不存在");
        }
        //判断该成员是否在该团队中
        List<Member> memberList = memberMapper.getMemberByGroupId(groupId);
        for (Member member : memberList) {
            //说明该成员在团队中
            if (member.getMemberId().equals(userId)) {
                return true;
            }
        }
        throw new ErrorException("您不是该团队的成员，无权访问");
    }
}