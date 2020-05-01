package com.sln.ontime.dao;

import com.sln.ontime.model.po.UserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatMapper {


    UserPo getUserByOpenId(String openId);

    Integer insertUser(UserPo userPo);


}
