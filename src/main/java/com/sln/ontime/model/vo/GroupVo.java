package com.sln.ontime.model.vo;


import com.sln.ontime.model.Member;
import com.sln.ontime.model.Plan;
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

    private List<Plan> groupPlanList;

    private List<Member> groupMemberList;

    private Integer limit;

}