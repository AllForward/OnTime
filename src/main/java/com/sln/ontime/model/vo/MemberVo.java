package com.sln.ontime.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-05-08 16:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVo {

    private Integer groupId;

    private String memberId;

    private String name;

    private String wechatIcon;

    private String type;

}