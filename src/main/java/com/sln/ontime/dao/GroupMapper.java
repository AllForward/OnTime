package com.sln.ontime.dao;


import com.sln.ontime.model.po.Group;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GroupMapper {

    @Select("select group_id, group_name, creator_id, `limit` from `group` where group_id = #{groupId}")
    Group getGroupByGroupId(Integer groupId);

    @Delete("delete from `group` where group_id = #{groupId}")
    Integer deleteGroupByGroupId(Integer groupId);

    @Insert("insert into `group`(group_name, `limit`, creator_id) values(#{groupName}, #{limit}, #{creatorId})")
    @Options(useGeneratedKeys=true, keyProperty="groupId", keyColumn="group_id")
    void insertGroup(Group group);
}
