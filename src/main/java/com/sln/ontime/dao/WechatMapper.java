package com.sln.ontime.dao;

import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.UserVo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WechatMapper {

    @Select("select user_id, open_id, name, wechat_icon from user where open_id = #{openId}")
    UserPo getUserByOpenId(String openId);

    @Insert("insert into user(open_id, name, wechat_icon) values(#{openId}, #{name}, #{wechatIcon})")
    Integer insertUser(UserVo userVo);

    @Select("select user_id, name, wechat_icon from user where user_id = #{userId}")
    UserPo getUserByUserId(Integer userId);


    @Update("update user set name = #{nickname} where user_id = #{userId}")
    Integer updateNickname(@Param("nickname")String nickname,@Param("userId")Integer userId);
}
