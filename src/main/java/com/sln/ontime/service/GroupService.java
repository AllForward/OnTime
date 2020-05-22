package com.sln.ontime.service;

import com.sln.ontime.model.po.Group;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;

public interface GroupService {

    GroupVo updateMember(MemberVo memberVo, UserPo userPo) throws Exception;

    GroupVo addGroup(Group group, UserPo userPo);

    String deleteGroup(Integer groupId, UserPo userPo);


}
