package com.sln.ontime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-05-06 14:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Plan {

    private Integer planId;

    private Integer userId;

    private String planName;

    //大计划类型标识(非0及为groupId,0为个人计划)
    private Integer type;

}
