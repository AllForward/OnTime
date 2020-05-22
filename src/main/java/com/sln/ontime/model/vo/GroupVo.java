package com.sln.ontime.model.vo;


import com.sln.ontime.model.po.Member;
import com.sln.ontime.model.po.Plan;
import com.sln.ontime.model.po.UserPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description
 * @author guopei
 * @date 2020-05-08 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupVo {

    private Integer groupId;

    private String groupName;

    private Integer creatorId;

    private List<Plan> groupPlanList;

    private List<UserPo> groupMemberList;

    private Integer limit;

}