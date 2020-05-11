package com.sln.ontime.service;

import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.GroupVo;
import com.sln.ontime.model.vo.MemberVo;
import com.sln.ontime.model.vo.UserVo;

public interface GroupService {

    GroupVo updateMember(MemberVo memberVo, UserPo userPo) throws Exception;


}
