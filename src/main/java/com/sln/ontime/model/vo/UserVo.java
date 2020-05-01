package com.sln.ontime.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-04-24 16:59
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVo {

    private String code;

    private Integer userId;

    //微信名
    private String name;

    //微信头像地址
    private String wechatIcon;


    private String openId;

    private String unionId;
}
