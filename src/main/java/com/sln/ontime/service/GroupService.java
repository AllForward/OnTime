package com.sln.ontime.service;

import com.sln.ontime.model.po.Group;
import com.sln.ontime.model.po.Plan;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.model.vo.PlanVo;

import java.util.List;

public interface GroupService {

    GroupVo updateMember(MemberVo memberVo, UserPo userPo) throws Exception;

    GroupVo addGroup(Group group, UserPo userPo);

    String deleteGroup(Integer groupId, UserPo userPo);

    List<Plan> getGroupPlan(Integer groupId, UserPo userPo);

    PlanVo addGroupPlan(PlanVo planVo);

    boolean deleteGroupPlan(Integer planId, UserPo userPo);


}
