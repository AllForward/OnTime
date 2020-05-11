package com.sln.ontime.dao;

import com.sln.ontime.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {

    @Select("select group_id, group_name, creator_id, limit from group where group_id = #{groupId}")
    Group getGroupByGroupId(Integer groupId);



}
