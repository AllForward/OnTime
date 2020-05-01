package com.sln.ontime.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 * @author guopei
 * @date 2020-04-24 17:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPo implements Serializable {

    private Integer userId;

    //微信名
    private String name;

    //微信头像地址
    private String wechatIcon;

    private String openId;

    private String unionId;

}
